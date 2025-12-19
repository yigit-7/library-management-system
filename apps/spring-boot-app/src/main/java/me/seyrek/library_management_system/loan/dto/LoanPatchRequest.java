package me.seyrek.library_management_system.loan.dto;

import java.time.Instant;

public record LoanPatchRequest(
        Instant dueDate,
        Instant returnDate
) {
}
