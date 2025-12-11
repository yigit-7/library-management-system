package me.seyrek.library_management_system.user.service;

import me.seyrek.library_management_system.user.dto.UserDto;
import me.seyrek.library_management_system.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> findAllUsers(Pageable pageable);
    UserDto findUserById(Long userId);
    UserDto findUserByEmail(String email);
    void validateEmailIsAvailable(String email);
    UserDto saveUser(User user);
}
