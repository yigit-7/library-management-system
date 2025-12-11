package me.seyrek.library_management_system.auth.service;

import me.seyrek.library_management_system.auth.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyRefreshToken(String token);
    void deleteByToken(String token);
}
