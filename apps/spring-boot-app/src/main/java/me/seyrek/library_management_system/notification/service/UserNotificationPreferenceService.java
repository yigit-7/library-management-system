package me.seyrek.library_management_system.notification.service;

import me.seyrek.library_management_system.notification.dto.UpdateNotificationPreferenceRequest;
import me.seyrek.library_management_system.notification.dto.UserNotificationPreferenceDto;
import me.seyrek.library_management_system.notification.model.NotificationCategory;
import me.seyrek.library_management_system.notification.model.NotificationChannel;

import java.util.List;
import java.util.Set;

public interface UserNotificationPreferenceService {
    List<UserNotificationPreferenceDto> getUserPreferences(Long userId);
    UserNotificationPreferenceDto updatePreference(Long userId, UpdateNotificationPreferenceRequest request);
    Set<NotificationChannel> getChannelsForUserAndCategory(Long userId, NotificationCategory category);
}
