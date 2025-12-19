package me.seyrek.library_management_system.fine.service;

import lombok.extern.slf4j.Slf4j;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.BusinessException;
import me.seyrek.library_management_system.exception.client.ResourceNotFoundException;
import me.seyrek.library_management_system.fine.dto.*;
import me.seyrek.library_management_system.fine.mapper.FineMapper;
import me.seyrek.library_management_system.fine.model.Fine;
import me.seyrek.library_management_system.fine.model.FineStatus;
import me.seyrek.library_management_system.fine.repository.FineRepository;
import me.seyrek.library_management_system.fine.repository.FineSpecification;
import me.seyrek.library_management_system.loan.model.Loan;
import me.seyrek.library_management_system.loan.repository.LoanRepository;
import me.seyrek.library_management_system.payment.dto.PaymentRequest;
import me.seyrek.library_management_system.payment.service.PaymentService;
import me.seyrek.library_management_system.user.model.User;
import me.seyrek.library_management_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@Transactional
public class FineServiceImpl implements FineService {

    private final BigDecimal maxUnpaidAmount;
    private final BigDecimal amountPerDay;

    private final FineRepository fineRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final FineMapper fineMapper;
    private final PaymentService paymentService;

    public FineServiceImpl(
            @Value("${application.library.fine.max-unpaid-amount}") BigDecimal maxUnpaidAmount,
            @Value("${application.library.fine.amount-per-day}") BigDecimal amountPerDay,
            FineRepository fineRepository,
            UserRepository userRepository,
            LoanRepository loanRepository,
            FineMapper fineMapper,
            PaymentService paymentService) {
        this.maxUnpaidAmount = maxUnpaidAmount;
        this.amountPerDay = amountPerDay;
        this.fineRepository = fineRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.fineMapper = fineMapper;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FineDto> getAllFines(FineSearchRequest request, Pageable pageable) {
        Specification<Fine> spec = FineSpecification.withDynamicQuery(request);
        return fineRepository.findAll(spec, pageable)
                .map(fineMapper::toFineDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FineDto> getMyFines(Long userId, FineUserSearchRequest userRequest, Pageable pageable) {
        FineSearchRequest adminRequest = fineMapper.toFineSearchRequest(userRequest, userId);
        Specification<Fine> spec = FineSpecification.withDynamicQuery(adminRequest);
        return fineRepository.findAll(spec, pageable)
                .map(fineMapper::toFineDto);
    }

    @Override
    @Transactional(readOnly = true)
    public FineDto getFineById(Long id) {
        return fineRepository.findById(id)
                .map(fineMapper::toFineDto)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + id, ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public FineDto createFine(FineCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.userId(), ErrorCode.USER_NOT_FOUND));

        Loan loan = null;
        if (request.loanId() != null) {
            loan = loanRepository.findById(request.loanId())
                    .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + request.loanId(), ErrorCode.RESOURCE_NOT_FOUND));
        }

        Fine fine = fineMapper.fromFineCreateRequest(request);
        fine.setUser(user);
        fine.setLoan(loan);
        fine.setFineDate(Instant.now());

        return fineMapper.toFineDto(fineRepository.save(fine));
    }

    @Override
    public void createOverdueFine(Loan loan) {
        if (loan == null || loan.getReturnDate() == null || !loan.getReturnDate().isAfter(loan.getDueDate())) {
            return;
        }

        long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
        
        if (overdueDays == 0) {
            overdueDays = 1;
        }

        BigDecimal fineAmount = amountPerDay.multiply(BigDecimal.valueOf(overdueDays));

        Fine fine = new Fine();
        fine.setUser(loan.getUser());
        fine.setLoan(loan);
        fine.setAmount(fineAmount);
        fine.setReason("Overdue book return for " + overdueDays + " days.");
        fine.setStatus(FineStatus.UNPAID);
        fine.setFineDate(loan.getReturnDate());

        fineRepository.save(fine);
    }

    @Override
    public void createFineForLostBook(Loan loan) {
        if (loan == null) {
            return;
        }

        BigDecimal bookPrice = loan.getCopy().getBook().getPrice();
        if (bookPrice == null) {
            throw new BusinessException("Book price for lost item cannot be null. Book ID: " + loan.getCopy().getBook().getId(), ErrorCode.BOOK_PRICE_NOT_FOUND);
        }

        Fine fine = new Fine();
        fine.setUser(loan.getUser());
        fine.setLoan(loan);
        fine.setAmount(bookPrice);
        fine.setReason("Lost book: " + loan.getCopy().getBook().getTitle());
        fine.setStatus(FineStatus.UNPAID);
        fine.setFineDate(Instant.now());

        fineRepository.save(fine);
    }

    @Override
    public void createFineForDamagedBook(Loan loan, BigDecimal amount, String description) {
        if (loan == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        Fine fine = new Fine();
        fine.setUser(loan.getUser());
        fine.setLoan(loan);
        fine.setAmount(amount);
        fine.setReason(StringUtils.hasText(description) ? "Damaged book: " + description : "Damaged book fine.");
        fine.setStatus(FineStatus.UNPAID);
        fine.setFineDate(loan.getReturnDate() != null ? loan.getReturnDate() : Instant.now());

        fineRepository.save(fine);
    }

    @Override
    public FineDto updateFine(Long id, FineUpdateRequest request) {
        Fine existingFine = findFineByIdOrThrow(id);
        fineMapper.updateFineFromRequest(request, existingFine);
        return fineMapper.toFineDto(fineRepository.save(existingFine));
    }

    @Override
    public FineDto patchFine(Long id, FinePatchRequest request) {
        Fine existingFine = findFineByIdOrThrow(id);
        fineMapper.patchFineFromRequest(request, existingFine);

        // Handle logic if status changes to PAID
        if (request.status() == FineStatus.PAID && existingFine.getPaymentDate() == null) {
            existingFine.setPaymentDate(Instant.now());
        } else if (request.status() == FineStatus.UNPAID) {
            existingFine.setPaymentDate(null);
        }

        return fineMapper.toFineDto(fineRepository.save(existingFine));
    }

    @Override
    public FineDto payFine(Long id, PaymentRequest request) {
        Fine fine;
        try {
            fine = fineRepository.findByIdWithLock(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + id, ErrorCode.RESOURCE_NOT_FOUND));
        } catch (PessimisticLockingFailureException e) {
            log.warn("Concurrent payment attempt detected for fine ID: {}", id);
            throw new BusinessException("Payment is currently being processed by another transaction. Please try again.", ErrorCode.PAYMENT_PROCESSING_ERROR);
        }

        if (fine.getStatus() == FineStatus.PAID) {
            throw new BusinessException("Fine is already paid.", ErrorCode.FINE_ALREADY_PAID);
        }

        // Tutarı onayla (kısmi ödeme yok tekte atacaksın)
        if (fine.getAmount().compareTo(request.amount()) != 0) {
            throw new BusinessException("Payment amount does not match fine amount.", ErrorCode.PAYMENT_AMOUNT_MISMATCH);
        }

        // Ödemeyi işle
        String transactionId = paymentService.processPayment(request.amount(), request.paymentToken());
        log.info("Payment successful for Fine ID: {}. Transaction ID: {}", id, transactionId);

        fine.setStatus(FineStatus.PAID);
        fine.setPaymentDate(Instant.now());
        
        return fineMapper.toFineDto(fineRepository.save(fine));
    }

    @Override
    public void deleteFine(Long id) {
        if (!fineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fine not found with id: " + id, ErrorCode.RESOURCE_NOT_FOUND);
        }
        // Optional: Check if fine is PAID before deleting?
        // For now, standard delete.
        fineRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFineLimitExceeded(Long userId) {
        BigDecimal totalUnpaidFines = fineRepository.sumFinesByUserIdAndStatus(userId, FineStatus.UNPAID);
        return totalUnpaidFines.compareTo(maxUnpaidAmount) > 0;
    }

    private Fine findFineByIdOrThrow(Long id) {
        return fineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + id, ErrorCode.RESOURCE_NOT_FOUND));
    }
}
