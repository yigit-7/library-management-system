package me.seyrek.library_management_system.notification.repository;

import me.seyrek.library_management_system.notification.model.NotificationCategory;
import me.seyrek.library_management_system.notification.model.UserNotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNotificationPreferenceRepository extends JpaRepository<UserNotificationPreference, Long> {
    List<UserNotificationPreference> findAllByUserId(Long userId);
    Optional<UserNotificationPreference> findByUserIdAndCategory(Long userId, NotificationCategory category);
}
