package me.seyrek.library_management_system.loan.service.rule;

import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.BusinessException;
import me.seyrek.library_management_system.fine.service.FineService;
import me.seyrek.library_management_system.user.model.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class FineLimitRule implements LoanCreationRule {

    private final FineService fineService;

    public FineLimitRule(FineService fineService) {
        this.fineService = fineService;
    }

    @Override
    public void validate(User user, Copy copy) {
        if (fineService.isFineLimitExceeded(user.getId())) {
            throw new BusinessException("User has exceeded the unpaid fine limit.", ErrorCode.USER_FINE_LIMIT_EXCEEDED);
        }
    }
}
