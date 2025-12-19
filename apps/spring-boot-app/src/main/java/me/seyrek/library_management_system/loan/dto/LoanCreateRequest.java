package me.seyrek.library_management_system.loan.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record LoanCreateRequest(
        @NotNull(message = "User ID cannot be null")
        Long userId,

        @NotNull(message = "Copy ID cannot be null")
        Long copyId,

        @Future(message = "Due date must be in the future")
        Instant dueDate
) {
}