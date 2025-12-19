package me.seyrek.library_management_system.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserBanRequest(
        @NotBlank(message = "Ban reason cannot be blank")
        String reason
) {
}
