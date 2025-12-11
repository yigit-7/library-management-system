package me.seyrek.library_management_system.user.controller;

import lombok.AllArgsConstructor;
import me.seyrek.library_management_system.common.ApiResponse;
import me.seyrek.library_management_system.security.SecurityUser;
import me.seyrek.library_management_system.user.dto.UserDto;
import me.seyrek.library_management_system.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me/profile")
    ApiResponse<UserDto> getMyProfile(@AuthenticationPrincipal SecurityUser securityUser) {
        return ApiResponse.success(userService.findUserById(securityUser.getUser().getId()));
    }

    @GetMapping("/{id}/profile")
    ApiResponse<UserDto> getUserById(@PathVariable Long id) {
        return ApiResponse.success(userService.findUserById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ApiResponse<Page<UserDto>> getAllUsers(
            @PageableDefault(size = 20, sort = "id")
            Pageable pageable
    ) {
        return ApiResponse.success(userService.findAllUsers(pageable));
    }
}
