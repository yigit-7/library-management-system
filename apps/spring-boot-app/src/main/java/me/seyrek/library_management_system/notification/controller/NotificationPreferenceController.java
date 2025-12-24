package me.seyrek.library_management_system.notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.seyrek.library_management_system.common.ApiResponse;
import me.seyrek.library_management_system.notification.dto.UpdateNotificationPreferenceRequest;
import me.seyrek.library_management_system.notification.dto.UserNotificationPreferenceDto;
import me.seyrek.library_management_system.notification.service.UserNotificationPreferenceService;
import me.seyrek.library_management_system.security.utils.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-preferences")
@RequiredArgsConstructor
public class NotificationPreferenceController {

    private final UserNotificationPreferenceService preferenceService;

    @GetMapping
    public ApiResponse<List<UserNotificationPreferenceDto>> getMyPreferences() {
        List<UserNotificationPreferenceDto> preferences = preferenceService.getUserPreferences(SecurityUtils.getCurrentUserId());
        return ApiResponse.success(preferences);
    }

    @PutMapping
    public ApiResponse<UserNotificationPreferenceDto> updatePreference(
            @Valid @RequestBody UpdateNotificationPreferenceRequest request) {
        
        UserNotificationPreferenceDto updatedPreference = preferenceService.updatePreference(SecurityUtils.getCurrentUserId(), request);
        return ApiResponse.success(updatedPreference);
    }
}
