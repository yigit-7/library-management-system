package me.seyrek.library_management_system.copy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.seyrek.library_management_system.copy.annotation.NotLoaned;
import me.seyrek.library_management_system.copy.model.CopyStatus;

public record CopyUpdateRequest(
        @NotBlank(message = "Barcode cannot be blank")
        String barcode,

        @NotNull(message = "Status cannot be null")
        @NotLoaned(message = "Copy status cannot be LOANED manually.")
        CopyStatus status
) {
}