package me.seyrek.library_management_system.fine.controller;

import jakarta.validation.Valid;
import me.seyrek.library_management_system.common.ApiResponse;
import me.seyrek.library_management_system.common.PagedData;
import me.seyrek.library_management_system.fine.dto.*;
import me.seyrek.library_management_system.fine.service.FineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/management/fines")
@PreAuthorize("hasRole('ADMIN')")
public class FineManagementController {

    private final FineService fineService;

    public FineManagementController(FineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping
    public ApiResponse<PagedData<FineDto>> getAllFines(
            FineSearchRequest request,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<FineDto> fines = fineService.getAllFines(request, pageable);
        return ApiResponse.success(PagedData.of(fines));
    }

    @GetMapping("/{id}")
    public ApiResponse<FineDto> getFineById(@PathVariable Long id) {
        return ApiResponse.success(fineService.getFineById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FineDto> createFine(@Valid @RequestBody FineCreateRequest request) {
        return ApiResponse.success(fineService.createFine(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<FineDto> updateFine(@PathVariable Long id, @Valid @RequestBody FineUpdateRequest request) {
        return ApiResponse.success(fineService.updateFine(id, request));
    }

    @PatchMapping("/{id}")
    public ApiResponse<FineDto> patchFine(@PathVariable Long id, @Valid @RequestBody FinePatchRequest request) {
        return ApiResponse.success(fineService.patchFine(id, request));
    }
}