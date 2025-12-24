package me.seyrek.library_management_system.notification.dto;

import jakarta.validation.constraints.NotNull;
import me.seyrek.library_management_system.notification.model.NotificationCategory;
import me.seyrek.library_management_system.notification.model.NotificationChannel;

import java.util.Set;

public record UpdateNotificationPreferenceRequest(
    @NotNull NotificationCategory category,
    @NotNull Set<NotificationChannel> channels
) {}
