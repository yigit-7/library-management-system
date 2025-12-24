package me.seyrek.library_management_system.copy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CopyCreateRequest(
        @NotNull(message = "Book ID cannot be null")
        Long bookId,

        @NotBlank(message = "Barcode cannot be blank")
        String barcode,

        String location
) {
}