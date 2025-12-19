package me.seyrek.library_management_system.loan.service;

import me.seyrek.library_management_system.loan.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface LoanService {
    // List methods return summary DTOs
    Page<LoanDto> getAllLoans(LoanSearchRequest request, Pageable pageable);
    Page<LoanUserSummaryDto> getMyLoans(Long userId, LoanUserSearchRequest request, Pageable pageable);

    // Single-item and action methods return detailed DTO
    LoanDetailDto getLoanById(Long id);
    LoanDetailDto createLoan(LoanCreateRequest request);
    LoanDetailDto returnLoan(Long id);
    LoanDetailDto markItemAsLost(Long id);
    LoanDetailDto markItemAsDamaged(Long id, BigDecimal damageAmount, String damageDescription);
    LoanDetailDto updateLoan(Long id, LoanUpdateRequest request);
    LoanDetailDto patchLoan(Long id, LoanPatchRequest request);

    void deleteLoan(Long id);
}
