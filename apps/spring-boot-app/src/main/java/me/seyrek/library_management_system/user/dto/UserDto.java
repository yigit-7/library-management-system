package me.seyrek.library_management_system.user.dto;

import me.seyrek.library_management_system.user.model.Role;
import java.util.Set;

public record UserDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        Set<Role> roles
) {
}
