package me.seyrek.library_management_system.fine.service;

import me.seyrek.library_management_system.fine.dto.*;
import me.seyrek.library_management_system.loan.model.Loan;
import me.seyrek.library_management_system.payment.dto.PaymentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface FineService {
    Page<FineDto> getAllFines(FineSearchRequest request, Pageable pageable);
    Page<FineDto> getMyFines(Long userId, FineUserSearchRequest request, Pageable pageable);
    FineDto getFineById(Long id);
    FineDto createFine(FineCreateRequest request);
    void createOverdueFine(Loan loan);
    void createFineForLostBook(Loan loan);
    void createFineForDamagedBook(Loan loan, BigDecimal amount, String description);
    FineDto updateFine(Long id, FineUpdateRequest request);
    FineDto patchFine(Long id, FinePatchRequest request);
    FineDto payFine(Long id, PaymentRequest request);
    void deleteFine(Long id);
    boolean isFineLimitExceeded(Long userId);
}
