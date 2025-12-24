package me.seyrek.library_management_system.loan.service.rule;

import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.copy.model.CopyStatus;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.BusinessException;
import me.seyrek.library_management_system.user.model.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class CopyAvailableRule implements LoanCreationRule {

    @Override
    public void validate(User user, Copy copy) {
        if (!copy.getStatus().equals(CopyStatus.AVAILABLE)) {
            throw new BusinessException("Copy is not available for loan. Current status of copy: " + copy.getStatus(), ErrorCode.COPY_NOT_AVAILABLE);
        }
    }
}
