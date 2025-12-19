package me.seyrek.library_management_system.loan.dto;

import me.seyrek.library_management_system.fine.dto.FineDto;
import me.seyrek.library_management_system.loan.model.LoanStatus;

import java.time.Instant;
import java.util.List;

public record LoanDetailDto(
        Long id,

        Long userId,
        String userFirstName,
        String userLastName,
        String userEmail,

        Long copyId,
        String copyBarcode,

        String bookTitle,
        String bookIsbn,
        String bookCoverUrl,

        LoanStatus status,

        Instant loanDate,
        Instant dueDate,
        Instant returnDate,
        boolean isOverdue,

        List<FineDto> fines
) {
}
