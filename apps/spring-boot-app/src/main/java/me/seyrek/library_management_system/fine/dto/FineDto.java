package me.seyrek.library_management_system.fine.dto;

import me.seyrek.library_management_system.fine.model.FineStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public record FineDto(
        Long id,
        Long userId,
        String userEmail,
        Long loanId,
        BigDecimal amount,
        String reason,
        FineStatus status,
        Instant fineDate,
        Instant paymentDate,
        Instant createdAt,
        Instant updatedAt
) {
}