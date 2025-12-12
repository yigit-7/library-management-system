package me.seyrek.library_management_system.user.service;

import lombok.AllArgsConstructor;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.DuplicateResourceException;
import me.seyrek.library_management_system.exception.client.ResourceNotFoundException;
import me.seyrek.library_management_system.user.dto.*;
import me.seyrek.library_management_system.user.mapper.UserMapper;
import me.seyrek.library_management_system.user.model.User;
import me.seyrek.library_management_system.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserPrivateProfile findPrivateProfile(Long id) {
        var user = findUserByIdOrThrow(id);
        return userMapper.toUserPrivateProfile(user);
    }

    @Override
    public UserPublicProfile findPublicProfile(Long id) {
        var user = findUserByIdOrThrow(id);
        return userMapper.toUserPublicProfile(user);
    }

    @Override
    public Page<UserDto> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserDto);
    }

    @Override
    public UserDto findUserById(Long userId) {
        var user = findUserByIdOrThrow(userId);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email, ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto saveUser(User user) {
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto createUser(UserCreateRequest request) {
        validateEmailIsAvailable(request.email());
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        return saveUser(user);
    }

    @Override
    public void validateEmailIsAvailable(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new DuplicateResourceException("Email already in use: " + email, ErrorCode.EMAIL_ALREADY_EXISTS);
        });
    }

    @Override
    @Transactional
    public UserEditProfileResponse editProfile(Long id, UserEditProfileRequest request) {
        var user = findUserByIdOrThrow(id);
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        return userMapper.toUserEditProfileResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserUpdateResponse updateUser(Long id, UserUpdateRequest request) {
        var user = findUserByIdOrThrow(id);

        if (!user.getEmail().equals(request.email())) {
            validateEmailIsAvailable(request.email());
            user.setEmail(request.email());
        }

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRoles(request.roles());

        return userMapper.toUserUpdateResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User findUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id, ErrorCode.USER_NOT_FOUND));
    }
}
