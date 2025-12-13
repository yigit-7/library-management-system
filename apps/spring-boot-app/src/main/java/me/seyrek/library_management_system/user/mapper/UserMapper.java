package me.seyrek.library_management_system.user.mapper;

import me.seyrek.library_management_system.auth.dto.RegisterRequest;
import me.seyrek.library_management_system.user.dto.*;
import me.seyrek.library_management_system.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    UserPrivateProfile toUserPrivateProfile(User user);

    UserPublicProfile toUserPublicProfile(User user);

    UserEditProfileResponse toUserEditProfileResponse(User user);

    UserUpdateResponse toUserUpdateResponse(User user);

    @Mapping(target = "password", ignore = true)
    User toUser(UserDto userDto);

    @Mapping(target = "roles", ignore = true) // Handled in the service layer
    User toUser(RegisterRequest registerRequest);

    @Mapping(target = "password", ignore = true)
    User toUser(UserCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUserFromEditProfileRequest(UserEditProfileRequest request, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);
}