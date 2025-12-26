package me.seyrek.library_management_system.copy.controller;

import lombok.RequiredArgsConstructor;
import me.seyrek.library_management_system.common.ApiResponse;
import me.seyrek.library_management_system.common.PagedData;
import me.seyrek.library_management_system.copy.dto.CopyDto;
import me.seyrek.library_management_system.copy.dto.CopySearchRequest;
import me.seyrek.library_management_system.copy.service.CopyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/copies")
@RequiredArgsConstructor
public class CopyController {

    private final CopyService copyService;

    @GetMapping
    public ApiResponse<PagedData<CopyDto>> getAllCopies(
            CopySearchRequest request,
            @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        Page<CopyDto> copies = copyService.getAllCopies(request, pageable);
        return ApiResponse.success(PagedData.of(copies));
    }

    @GetMapping("/{id}")
    public ApiResponse<CopyDto> getCopyById(@PathVariable Long id) {
        return ApiResponse.success(copyService.getCopyById(id));
    }

    @GetMapping("/by-barcode/{barcode}")
    public ApiResponse<CopyDto> getCopyByBarcode(@PathVariable String barcode) {
        return ApiResponse.success(copyService.getCopyByBarcode(barcode));
    }
}