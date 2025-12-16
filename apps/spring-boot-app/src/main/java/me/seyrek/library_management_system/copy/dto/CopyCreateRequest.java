package me.seyrek.library_management_system.copy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CopyCreateRequest(
        @NotNull(message = "Book ID cannot be null")
        Long bookId,

        @NotBlank(message = "Barcode cannot be blank")
        String barcode
) {
}