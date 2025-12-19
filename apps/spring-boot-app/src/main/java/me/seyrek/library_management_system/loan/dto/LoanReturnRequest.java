package me.seyrek.library_management_system.loan.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record LoanReturnRequest(
        @NotNull(message = "Loan ID cannot be null")
        Long loanId
) {
}