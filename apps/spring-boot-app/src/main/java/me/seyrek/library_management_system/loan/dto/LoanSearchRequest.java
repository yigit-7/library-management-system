package me.seyrek.library_management_system.loan.dto;

import me.seyrek.library_management_system.loan.model.LoanStatus;

import java.time.Instant;

public record LoanSearchRequest(
        // about the user
        Long userId,
        String userEmail,

        // book / copy
        Long copyId, // data ID
        String barcode, // natural ID
        Long bookId, // data ID
        String isbn, // natural ID
        String bookTitle,

        // loan status
        LoanStatus status,
        Boolean overdue,

        // date range
        Instant loanDateStart,
        Instant loanDateEnd,
        Instant dueDateStart,
        Instant dueDateEnd,
        Instant returnDateStart,
        Instant returnDateEnd
) {
}
