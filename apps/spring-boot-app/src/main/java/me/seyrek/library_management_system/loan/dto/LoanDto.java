package me.seyrek.library_management_system.loan.dto;

import me.seyrek.library_management_system.loan.model.LoanStatus;

import java.time.Instant;
// TODO: make this DTO only consist its own field as basic
// TODO: implement composition DTOs for all the domains like this
//public record UserSummaryDto(String firstName, String lastName, String email) {}
//
//public record LoanDto(
//        Long id,
//        UserSummaryDto user, // User alanlarını tekrar yazmak yerine bu record'u kullanın!
//        String bookTitle
//) {}
//
//public record LoanDetailDto(
//        Long id,
//        UserSummaryDto user, // Aynı record burada da var.
//        String bookTitle,
//        List<FineDto> fines
//) {}
public record LoanDto(
        Long id,
        Long userId,
        String userEmail,
        Long copyId,
        String bookTitle,
        String bookIsbn,
        String bookCoverUrl,
        LoanStatus status,
        Instant loanDate,
        Instant dueDate,
        Instant returnDate,
        boolean isOverdue
) {
}
