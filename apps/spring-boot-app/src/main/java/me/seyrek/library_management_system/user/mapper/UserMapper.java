package me.seyrek.library_management_system.user.mapper;

import me.seyrek.library_management_system.auth.dto.RegisterRequest;
import me.seyrek.library_management_system.user.dto.UserDto;
import me.seyrek.library_management_system.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User toUser(UserDto userDto);

    @Mapping(target = "roles", ignore = true) // Handled in service layer
    User toUser(RegisterRequest registerRequest);
}
