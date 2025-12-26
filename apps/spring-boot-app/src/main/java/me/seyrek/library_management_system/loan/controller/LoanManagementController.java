package me.seyrek.library_management_system.loan.controller;

import jakarta.validation.Valid;
import me.seyrek.library_management_system.common.ApiResponse;
import me.seyrek.library_management_system.common.PagedData;
import me.seyrek.library_management_system.loan.dto.*;
import me.seyrek.library_management_system.loan.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/management/loans")
public class LoanManagementController {

    private final LoanService loanService;

    public LoanManagementController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<PagedData<LoanDto>> getAllLoans(
            LoanSearchRequest request,
            @PageableDefault(size = 20, sort = "loanDate") Pageable pageable
    ) {
        Page<LoanDto> loans = loanService.getAllLoans(request, pageable);
        return ApiResponse.success(PagedData.of(loans));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<LoanDetailDto> getLoanById(@PathVariable Long id) {
        return ApiResponse.success(loanService.getLoanById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<LoanDetailDto> createLoan(@Valid @RequestBody LoanCreateRequest request) {
        return ApiResponse.success(loanService.createLoan(request));
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<LoanDetailDto> returnLoan(@PathVariable Long id) {
        return ApiResponse.success(loanService.returnLoan(id));
    }

    @PostMapping("/{id}/report-lost")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<LoanDetailDto> reportLost(@PathVariable Long id) {
        return ApiResponse.success(loanService.markItemAsLost(id));
    }

    @PostMapping("/{id}/report-damaged")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<LoanDetailDto> reportDamaged(@PathVariable Long id, @Valid @RequestBody LoanReportDamagedRequest request) {
        return ApiResponse.success(loanService.markItemAsDamaged(id, request.damageAmount(), request.damageDescription()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<LoanDetailDto> updateLoan(@PathVariable Long id, @Valid @RequestBody LoanUpdateRequest request) {
        return ApiResponse.success(loanService.updateLoan(id, request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<LoanDetailDto> patchLoan(@PathVariable Long id, @Valid @RequestBody LoanPatchRequest request) {
        return ApiResponse.success(loanService.patchLoan(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ApiResponse.success("Loan with ID " + id + " has been successfully deleted");
    }
}