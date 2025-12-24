package me.seyrek.library_management_system.copy.dto;

import me.seyrek.library_management_system.copy.annotation.NotLoaned;
import me.seyrek.library_management_system.copy.model.CopyStatus;

public record CopyPatchRequest(
        String barcode,

        @NotLoaned(message = "Copy status cannot be LOANED manually.")
        CopyStatus status,

        String location
) {
}
