package me.seyrek.library_management_system.loan.service.rule;

import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.BusinessException;
import me.seyrek.library_management_system.user.model.User;
import me.seyrek.library_management_system.user.model.UserStatus;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class UserActiveRule implements LoanCreationRule {

    @Override
    public void validate(User user, Copy copy) {
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BusinessException("User account is not active. Status: " + user.getStatus(), ErrorCode.USER_ACCOUNT_LOCKED);
        }
    }
}
