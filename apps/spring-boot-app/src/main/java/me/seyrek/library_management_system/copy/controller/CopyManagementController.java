package me.seyrek.library_management_system.copy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.seyrek.library_management_system.common.ApiResponse;
import me.seyrek.library_management_system.copy.dto.CopyCreateRequest;
import me.seyrek.library_management_system.copy.dto.CopyDto;
import me.seyrek.library_management_system.copy.dto.CopyPatchRequest;
import me.seyrek.library_management_system.copy.dto.CopyUpdateRequest;
import me.seyrek.library_management_system.copy.service.CopyService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/management/copies")
@RequiredArgsConstructor
public class CopyManagementController {

    private final CopyService copyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<CopyDto> createCopy(@Valid @RequestBody CopyCreateRequest request) {
        return ApiResponse.success(copyService.createCopy(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<CopyDto> updateCopy(@PathVariable Long id, @Valid @RequestBody CopyUpdateRequest request) {
        return ApiResponse.success(copyService.updateCopy(id, request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<CopyDto> patchCopy(@PathVariable Long id, @Valid @RequestBody CopyPatchRequest request) {
        return ApiResponse.success(copyService.patchCopy(id, request));
    }

    @PostMapping("/{id}/retire")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ApiResponse<Void> retireCopy(@PathVariable Long id) {
        copyService.retireCopy(id);
        return ApiResponse.success("Copy retired (soft deleted) successfully");
    }
}