package me.seyrek.library_management_system.loan.dto;

import me.seyrek.library_management_system.fine.dto.FineDto;
import me.seyrek.library_management_system.loan.model.LoanStatus;

import java.time.Instant;
import java.util.List;

public record LoanUserSummaryDto(
        Long id,
        String bookTitle,
//        String bookIsbn,
        String bookCoverUrl,
        LoanStatus status,
        Instant loanDate,
        Instant dueDate,
        Instant returnDate,
        boolean isOverdue,

        // TODO: simplify fine data like sum of the fines or is there fine tough?
        // Maybe add a DTO that has fineId, totalUnpaidFineAmount or both?
        List<FineDto> fines
) {
}
