package me.seyrek.library_management_system.copy.service;

import me.seyrek.library_management_system.copy.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CopyService {
    Page<CopyDto> getAllCopies(CopySearchRequest request, Pageable pageable);
    Page<CopyDto> getCopiesByBookId(Long bookId, Pageable pageable);
    CopyDto getCopyById(Long id);
    CopyDto getCopyByBarcode(String barcode);
    CopyDto createCopy(CopyCreateRequest request);
    CopyDto updateCopy(Long id, CopyUpdateRequest request);
    void retireCopy(Long id);
    CopyDto patchCopy(Long id, CopyPatchRequest request);
}