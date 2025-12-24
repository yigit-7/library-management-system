package me.seyrek.library_management_system.fine.service.strategy;

import me.seyrek.library_management_system.loan.model.Loan;

import java.math.BigDecimal;

public interface OverdueFineCalculationStrategy {
    BigDecimal calculateFine(Loan loan);
}
