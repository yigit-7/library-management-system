package me.seyrek.library_management_system.loan.service.rule;

import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.user.model.User;

public interface LoanCreationRule {
    void validate(User user, Copy copy);
}
