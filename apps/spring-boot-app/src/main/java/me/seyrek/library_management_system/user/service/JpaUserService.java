package me.seyrek.library_management_system.user.service;

import lombok.AllArgsConstructor;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.DuplicateResourceException;
import me.seyrek.library_management_system.exception.client.ResourceNotFoundException;
import me.seyrek.library_management_system.user.dto.UserDto;
import me.seyrek.library_management_system.user.mapper.UserMapper;
import me.seyrek.library_management_system.user.model.User;
import me.seyrek.library_management_system.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserDto> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserDto);
    }

    @Override
    public UserDto findUserById(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId, ErrorCode.USER_NOT_FOUND));

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
    public void validateEmailIsAvailable(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new DuplicateResourceException("Email already in use: " + email, ErrorCode.EMAIL_ALREADY_EXISTS);
        });
    }
}
