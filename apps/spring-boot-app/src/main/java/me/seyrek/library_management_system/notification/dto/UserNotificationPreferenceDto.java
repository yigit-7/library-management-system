package me.seyrek.library_management_system.notification.dto;

import lombok.Builder;
import me.seyrek.library_management_system.notification.model.NotificationCategory;
import me.seyrek.library_management_system.notification.model.NotificationChannel;

import java.util.Set;

@Builder
public record UserNotificationPreferenceDto(
    NotificationCategory category,
    Set<NotificationChannel> channels
) {}
