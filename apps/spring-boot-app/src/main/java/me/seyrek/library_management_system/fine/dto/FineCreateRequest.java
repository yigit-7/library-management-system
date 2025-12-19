package me.seyrek.library_management_system.fine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record FineCreateRequest(
        @NotNull(message = "User ID cannot be null")
        Long userId,

        Long loanId,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotBlank(message = "Reason cannot be blank")
        String reason
) {
}