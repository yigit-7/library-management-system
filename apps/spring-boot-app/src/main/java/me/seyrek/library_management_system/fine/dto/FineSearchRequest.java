package me.seyrek.library_management_system.fine.dto;

import me.seyrek.library_management_system.fine.model.FineStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record FineSearchRequest(
        // User Filters
        Long userId,
        String userEmail,

        // Related Item Filters
        Long loanId,
        Long bookId,

        // Fine Status & Amount Filters
        FineStatus status,
        BigDecimal minAmount,
        BigDecimal maxAmount,

        // Date Range Filters
        Instant fineDateStart,
        Instant fineDateEnd,
        Instant paymentDateStart,
        Instant paymentDateEnd
) {
}
