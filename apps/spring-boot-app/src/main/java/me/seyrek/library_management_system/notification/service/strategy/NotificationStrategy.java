package me.seyrek.library_management_system.notification.service.strategy;

import me.seyrek.library_management_system.notification.model.NotificationChannel;
import me.seyrek.library_management_system.notification.model.NotificationRequest;

public interface NotificationStrategy {
    void send(NotificationRequest request);
    NotificationChannel supports();
}
