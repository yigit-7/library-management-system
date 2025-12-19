package me.seyrek.library_management_system.loan.service;

import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.copy.model.CopyStatus;
import me.seyrek.library_management_system.copy.service.CopyService;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.BusinessException;
import me.seyrek.library_management_system.exception.client.ResourceNotFoundException;
import me.seyrek.library_management_system.fine.service.FineService;
import me.seyrek.library_management_system.loan.dto.*;
import me.seyrek.library_management_system.loan.mapper.LoanMapper;
import me.seyrek.library_management_system.loan.model.Loan;
import me.seyrek.library_management_system.loan.model.LoanStatus;
import me.seyrek.library_management_system.loan.repository.LoanRepository;
import me.seyrek.library_management_system.loan.repository.LoanSpecification;
import me.seyrek.library_management_system.user.model.User;
import me.seyrek.library_management_system.user.model.UserStatus;
import me.seyrek.library_management_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final int maxActiveLoans;
    private final int dueDays;

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final CopyService copyService;
    private final FineService fineService;
    private final LoanMapper loanMapper;

    public LoanServiceImpl(
            @Value("${application.library.loan.max-active}") int maxActiveLoans,
            @Value("${application.library.loan.due-days}") int dueDays,
            LoanRepository loanRepository,
            UserRepository userRepository,
            CopyService copyService,
            FineService fineService,
            LoanMapper loanMapper) {
        this.maxActiveLoans = maxActiveLoans;
        this.dueDays = dueDays;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.copyService = copyService;
        this.fineService = fineService;
        this.loanMapper = loanMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoanDto> getAllLoans(LoanSearchRequest request, Pageable pageable) {
        Specification<Loan> spec = LoanSpecification.withDynamicQuery(request);
        return loanRepository.findAll(spec, pageable)
                .map(loanMapper::toLoanDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoanUserSummaryDto> getMyLoans(Long currentUserId, LoanUserSearchRequest userRequest, Pageable pageable) {
        LoanSearchRequest comprehensiveRequest = loanMapper.toLoanSearchRequest(userRequest, currentUserId);
        Specification<Loan> spec = LoanSpecification.withDynamicQuery(comprehensiveRequest);
        return loanRepository.findAll(spec, pageable)
                .map(loanMapper::toLoanUserSummaryDto);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanDetailDto getLoanById(Long id) {
        return loanRepository.findById(id)
                .map(loanMapper::toLoanDetailDto)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id, ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public LoanDetailDto createLoan(LoanCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.userId(), ErrorCode.USER_NOT_FOUND));

        Copy copy = copyService.getCopyEntityById(request.copyId());

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BusinessException("User account is not active. Status: " + user.getStatus(), ErrorCode.USER_ACCOUNT_LOCKED);
        }

        if (!copy.getStatus().equals(CopyStatus.AVAILABLE)) {
            throw new BusinessException("Copy is not available for loan. Current status of copy: " + copy.getStatus(), ErrorCode.COPY_NOT_AVAILABLE);
        }

        if (fineService.isFineLimitExceeded(request.userId())) {
            throw new BusinessException("User has exceeded the unpaid fine limit.", ErrorCode.USER_FINE_LIMIT_EXCEEDED);
        }

        if (loanRepository.countByUserIdAndStatus(request.userId(), LoanStatus.ACTIVE) >= maxActiveLoans) {
            throw new BusinessException("User has reached the maximum number of active loans (limit: " + maxActiveLoans + ").", ErrorCode.USER_LOAN_LIMIT_EXCEEDED);
        }

        if (loanRepository.existsByCopy_Book_IdAndStatus(copy.getBook().getId(), LoanStatus.ACTIVE)) {
            throw new BusinessException("User already has an active loan on this book.", ErrorCode.LOAN_ALREADY_EXISTS);
        }

        copyService.loanCopy(copy.getId());

        Loan loan = loanMapper.fromLoanCreateRequest(request);
        loan.setUser(user);
        loan.setCopy(copy);
        loan.setLoanDate(Instant.now());
        loan.setStatus(LoanStatus.ACTIVE);

        Instant dueDate = Optional.ofNullable(request.dueDate())
                .orElse(Instant.now().plus(dueDays, ChronoUnit.DAYS));
        loan.setDueDate(dueDate);

        // TODO: Read-After-Write logic similar cases
        return loanMapper.toLoanDetailDto(loanRepository.save(loan));
    }

    // TODO: add scheduler to mark loans as overdue
    @Override
    public LoanDetailDto returnLoan(Long id) {
        Loan loan = findLoanByIdOrThrow(id);

        if (loan.getStatus() != LoanStatus.ACTIVE && loan.getStatus() != LoanStatus.OVERDUE) {
            throw new BusinessException("This loan is not active and cannot be returned. Current status: " + loan.getStatus(), ErrorCode.LOAN_NOT_ACTIVE);
        }

        loan.setReturnDate(Instant.now());

        boolean isOverdue = loan.getReturnDate().isAfter(loan.getDueDate());
        loan.setStatus(isOverdue ? LoanStatus.RETURNED_OVERDUE : LoanStatus.RETURNED);

        if (isOverdue) {
            fineService.createOverdueFine(loan);
        }

        copyService.returnCopy(loan.getCopy().getId());

        return loanMapper.toLoanDetailDto(loanRepository.save(loan));
    }

    @Override
    public LoanDetailDto markItemAsLost(Long id) {
        Loan loan = findLoanByIdOrThrow(id);

        if (loan.getStatus() != LoanStatus.ACTIVE && loan.getStatus() != LoanStatus.OVERDUE) {
            throw new BusinessException("This loan is not active and cannot be reported as lost. Current status: " + loan.getStatus(), ErrorCode.LOAN_NOT_ACTIVE);
        }

        loan.setStatus(LoanStatus.LOST);
        
        copyService.reportLost(loan.getCopy().getId());
        fineService.createFineForLostBook(loan);

        return loanMapper.toLoanDetailDto(loanRepository.save(loan));
    }

    @Override
    public LoanDetailDto markItemAsDamaged(Long id, BigDecimal damageAmount, String damageDescription) {
        Loan loan = findLoanByIdOrThrow(id);

        if (loan.getStatus() != LoanStatus.ACTIVE && loan.getStatus() != LoanStatus.OVERDUE) {
            throw new BusinessException("This loan is not active and cannot be returned as damaged. Current status: " + loan.getStatus(), ErrorCode.LOAN_NOT_ACTIVE);
        }

        loan.setReturnDate(Instant.now());
        loan.setStatus(LoanStatus.RETURNED_DAMAGED);

        if (loan.getReturnDate().isAfter(loan.getDueDate())) {
            fineService.createOverdueFine(loan);
        }
        fineService.createFineForDamagedBook(loan, damageAmount, damageDescription);

        copyService.reportDamaged(loan.getCopy().getId());

        return loanMapper.toLoanDetailDto(loanRepository.save(loan));
    }

    @Override
    public LoanDetailDto updateLoan(Long id, LoanUpdateRequest request) {
        Loan existingLoan = findLoanByIdOrThrow(id);
        loanMapper.updateLoanFromRequest(request, existingLoan);
        return loanMapper.toLoanDetailDto(loanRepository.save(existingLoan));
    }

    @Override
    public LoanDetailDto patchLoan(Long id, LoanPatchRequest request) {
        Loan existingLoan = findLoanByIdOrThrow(id);
        loanMapper.patchLoanFromRequest(request, existingLoan);
        return loanMapper.toLoanDetailDto(loanRepository.save(existingLoan));
    }

    @Override
    public void deleteLoan(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Loan not found with id: " + id, ErrorCode.RESOURCE_NOT_FOUND);
        }
        loanRepository.deleteById(id);
    }

    private Loan findLoanByIdOrThrow(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id, ErrorCode.RESOURCE_NOT_FOUND));
    }
}
