package me.seyrek.library_management_system.user.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.seyrek.library_management_system.common.ApiResponse;
import me.seyrek.library_management_system.security.utils.SecurityUtils;
import me.seyrek.library_management_system.user.dto.UserEditProfileRequest;
import me.seyrek.library_management_system.user.dto.UserEditProfileResponse;
import me.seyrek.library_management_system.user.dto.UserPrivateProfile;
import me.seyrek.library_management_system.user.dto.UserPublicProfile;
import me.seyrek.library_management_system.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserPrivateProfile> getMyProfile() {
        return ApiResponse.success(userService.findPrivateProfile(SecurityUtils.getCurrentUserId()));
    }

    @PutMapping("/me")
    public ApiResponse<UserEditProfileResponse> editMyProfile(@Valid @RequestBody UserEditProfileRequest request) {
        return ApiResponse.success(userService.editProfile(SecurityUtils.getCurrentUserId(), request));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserPublicProfile> getUserPublicProfile(@PathVariable Long id) {
        return ApiResponse.success(userService.findPublicProfile(id));
    }
}
