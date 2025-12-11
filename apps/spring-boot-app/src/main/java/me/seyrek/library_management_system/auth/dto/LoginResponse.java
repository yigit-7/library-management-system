package me.seyrek.library_management_system.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {}
