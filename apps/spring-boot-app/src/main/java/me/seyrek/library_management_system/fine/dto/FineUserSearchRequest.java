package me.seyrek.library_management_system.fine.dto;

import me.seyrek.library_management_system.fine.model.FineStatus;

public record FineUserSearchRequest(
        FineStatus status
) {
}
