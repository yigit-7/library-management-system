package me.seyrek.library_management_system.copy.dto;

import jakarta.validation.constraints.Pattern;
import me.seyrek.library_management_system.copy.annotation.NotLoaned;
import me.seyrek.library_management_system.copy.model.CopyStatus;

public record CopyPatchRequest(
        String barcode,

        @NotLoaned(message = "Copy status cannot be LOANED manually.")
        CopyStatus status
) {
}
