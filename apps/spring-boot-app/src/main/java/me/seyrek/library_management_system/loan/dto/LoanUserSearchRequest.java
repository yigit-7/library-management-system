package me.seyrek.library_management_system.loan.dto;

import me.seyrek.library_management_system.loan.model.LoanStatus;

public record LoanUserSearchRequest(
        String bookTitle,
        String isbn,
        LoanStatus status,
        Boolean overdue
) {
}
