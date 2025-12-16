package me.seyrek.library_management_system.copy.dto;

import me.seyrek.library_management_system.book.dto.BookShortDto;
import me.seyrek.library_management_system.copy.model.CopyStatus;

public record CopyDto(
        Long id,
        BookShortDto book,
        String barcode,
        CopyStatus status
) {
}