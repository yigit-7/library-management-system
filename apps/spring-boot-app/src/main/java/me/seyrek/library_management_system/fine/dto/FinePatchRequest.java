package me.seyrek.library_management_system.fine.dto;

import jakarta.validation.constraints.Positive;
import me.seyrek.library_management_system.fine.model.FineStatus;

import java.math.BigDecimal;

public record FinePatchRequest(
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        String reason,

        FineStatus status
) {
}