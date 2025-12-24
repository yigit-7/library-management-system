package me.seyrek.library_management_system.notification.mapper;

import me.seyrek.library_management_system.notification.dto.UserNotificationPreferenceDto;
import me.seyrek.library_management_system.notification.model.UserNotificationPreference;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserNotificationPreferenceMapper {

    UserNotificationPreferenceDto toDto(UserNotificationPreference preference);
}
