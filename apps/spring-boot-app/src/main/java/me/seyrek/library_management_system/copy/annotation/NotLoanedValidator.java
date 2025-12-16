package me.seyrek.library_management_system.copy.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import me.seyrek.library_management_system.copy.model.CopyStatus;

public class NotLoanedValidator implements ConstraintValidator<NotLoaned, CopyStatus> {
    @Override
    public boolean isValid(CopyStatus value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value != CopyStatus.LOANED;
    }
}