package me.seyrek.library_management_system.notification.service;

import me.seyrek.library_management_system.notification.model.NotificationRequest;

public interface NotificationService {
    void send(NotificationRequest request);
}
