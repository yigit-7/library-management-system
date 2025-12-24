package me.seyrek.library_management_system.config;

import lombok.Data;
import me.seyrek.library_management_system.notification.model.NotificationCategory;
import me.seyrek.library_management_system.notification.model.NotificationChannel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.notification")
public class NotificationProperties {

    /**
     * Maps notification categories to their default channels.
     * Example in properties:
     * app.notification.defaults.LOAN_OVERDUE=EMAIL,SMS
     */
    private Map<NotificationCategory, Set<NotificationChannel>> defaults = new EnumMap<>(NotificationCategory.class);
}
