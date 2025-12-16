package me.seyrek.library_management_system.copy.dto;

import me.seyrek.library_management_system.copy.model.CopyStatus;

public record CopySearchRequest(
        String barcode,
        String isbn,
        Long bookId,
        CopyStatus copyStatus
) {
}