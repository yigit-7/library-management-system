package me.seyrek.library_management_system.user.service;

import me.seyrek.library_management_system.user.dto.*;
import me.seyrek.library_management_system.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserPrivateProfile findPrivateProfile(Long id);

    UserPublicProfile findPublicProfile(Long id);

    Page<UserDto> findAllUsers(Pageable pageable);

    UserDto findUserById(Long userId);

    UserDto findUserByEmail(String email);

    void validateEmailIsAvailable(String email);

    UserDto saveUser(User user);

    UserDto createUser(UserCreateRequest request);

    UserEditProfileResponse editProfile(Long id, UserEditProfileRequest request);

    UserUpdateResponse updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);

    void banUser(Long id, String reason);

    void unbanUser(Long id, String reason);
}
