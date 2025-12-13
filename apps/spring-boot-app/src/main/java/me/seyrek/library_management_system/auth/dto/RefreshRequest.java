package me.seyrek.library_management_system.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank(message = "Refresh token cannot be blank")
        String refreshToken
) {
}