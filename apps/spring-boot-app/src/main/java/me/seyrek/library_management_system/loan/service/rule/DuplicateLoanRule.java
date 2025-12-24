package me.seyrek.library_management_system.loan.service.rule;

import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.BusinessException;
import me.seyrek.library_management_system.loan.model.LoanStatus;
import me.seyrek.library_management_system.loan.repository.LoanRepository;
import me.seyrek.library_management_system.user.model.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class DuplicateLoanRule implements LoanCreationRule {

    private final LoanRepository loanRepository;

    public DuplicateLoanRule(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public void validate(User user, Copy copy) {
        if (loanRepository.existsByCopy_Book_IdAndStatus(copy.getBook().getId(), LoanStatus.ACTIVE)) {
            throw new BusinessException("User already has an active loan on this book.", ErrorCode.LOAN_ALREADY_EXISTS);
        }
    }
}
