package me.seyrek.library_management_system.notification.service;

import lombok.RequiredArgsConstructor;
import me.seyrek.library_management_system.config.NotificationProperties;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.ResourceNotFoundException;
import me.seyrek.library_management_system.notification.dto.UpdateNotificationPreferenceRequest;
import me.seyrek.library_management_system.notification.dto.UserNotificationPreferenceDto;
import me.seyrek.library_management_system.notification.mapper.UserNotificationPreferenceMapper;
import me.seyrek.library_management_system.notification.model.NotificationCategory;
import me.seyrek.library_management_system.notification.model.NotificationChannel;
import me.seyrek.library_management_system.notification.model.UserNotificationPreference;
import me.seyrek.library_management_system.notification.repository.UserNotificationPreferenceRepository;
import me.seyrek.library_management_system.user.model.User;
import me.seyrek.library_management_system.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserNotificationPreferenceServiceImpl implements UserNotificationPreferenceService {

    private final UserNotificationPreferenceRepository preferenceRepository;
    private final UserRepository userRepository;
    private final UserNotificationPreferenceMapper preferenceMapper;
    private final NotificationProperties notificationProperties;

    @Override
    @Transactional(readOnly = true)
    public List<UserNotificationPreferenceDto> getUserPreferences(Long userId) {
        List<UserNotificationPreference> preferences = preferenceRepository.findAllByUserId(userId);
        
        return preferences.stream()
                .map(preferenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserNotificationPreferenceDto updatePreference(Long userId, UpdateNotificationPreferenceRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId, ErrorCode.USER_NOT_FOUND));

        UserNotificationPreference preference = preferenceRepository.findByUserIdAndCategory(userId, request.category())
                .orElse(UserNotificationPreference.builder()
                        .user(user)
                        .category(request.category())
                        .build());

        preference.setChannels(request.channels());
        UserNotificationPreference savedPreference = preferenceRepository.save(preference);

        return preferenceMapper.toDto(savedPreference);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<NotificationChannel> getChannelsForUserAndCategory(Long userId, NotificationCategory category) {
        Optional<UserNotificationPreference> preferenceOpt = preferenceRepository.findByUserIdAndCategory(userId, category);

        if (preferenceOpt.isPresent()) {
            return preferenceOpt.get().getChannels();
        }

        return getDefaultChannels(category);
    }

    private Set<NotificationChannel> getDefaultChannels(NotificationCategory category) {
        Set<NotificationChannel> channels = notificationProperties.getDefaults().get(category);
        
        // Fallback to EMAIL if configuration is missing for a category
        if (channels == null || channels.isEmpty()) {
            return Set.of(NotificationChannel.EMAIL);
        }
        
        return channels;
    }
}
