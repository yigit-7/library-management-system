package me.seyrek.library_management_system.auth.service;

import me.seyrek.library_management_system.auth.dto.*;

public interface AuthService {

    void register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    RefreshResponse refresh(RefreshRequest refreshRequest);
    void logout(LogoutRequest logoutRequest);
}
