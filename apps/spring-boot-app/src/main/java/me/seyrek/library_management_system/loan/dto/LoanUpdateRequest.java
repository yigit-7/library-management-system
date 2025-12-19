package me.seyrek.library_management_system.loan.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record LoanUpdateRequest(
        @NotNull(message = "Due date cannot be null")
        Instant dueDate,

        Instant returnDate
) {
}