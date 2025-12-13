package me.seyrek.library_management_system.auth.dto;

public record RefreshResponse(
        String accessToken,
        String refreshToken
) {}
