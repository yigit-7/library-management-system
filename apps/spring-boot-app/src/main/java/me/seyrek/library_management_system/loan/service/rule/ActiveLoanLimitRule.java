package me.seyrek.library_management_system.loan.service.rule;

import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.BusinessException;
import me.seyrek.library_management_system.loan.model.LoanStatus;
import me.seyrek.library_management_system.loan.repository.LoanRepository;
import me.seyrek.library_management_system.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class ActiveLoanLimitRule implements LoanCreationRule {

    private final LoanRepository loanRepository;
    private final int maxActiveLoans;

    public ActiveLoanLimitRule(LoanRepository loanRepository,
                               @Value("${application.library.loan.max-active}") int maxActiveLoans) {
        this.loanRepository = loanRepository;
        this.maxActiveLoans = maxActiveLoans;
    }

    @Override
    public void validate(User user, Copy copy) {
        if (loanRepository.countByUserIdAndStatus(user.getId(), LoanStatus.ACTIVE) >= maxActiveLoans) {
            throw new BusinessException("User has reached the maximum number of active loans (limit: " + maxActiveLoans + ").", ErrorCode.USER_LOAN_LIMIT_EXCEEDED);
        }
    }
}
