package me.seyrek.library_management_system.loan.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LoanReportDamagedRequest(
        @NotNull(message = "Damage amount cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Damage amount must be greater than 0")
        BigDecimal damageAmount,

        @Size(max = 500, message = "Description can be at most 500 characters")
        String damageDescription
) {
}
