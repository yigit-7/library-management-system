package me.seyrek.library_management_system.author.controller;

import jakarta.validation.Valid;
import me.seyrek.library_management_system.author.dto.AuthorCreateRequest;
import me.seyrek.library_management_system.author.dto.AuthorDto;
import me.seyrek.library_management_system.author.dto.AuthorUpdateRequest;
import me.seyrek.library_management_system.author.service.AuthorService;
import me.seyrek.library_management_system.common.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/management/authors")
@PreAuthorize("hasRole('ADMIN')")
public class AuthorManagementController {

    private final AuthorService authorService;

    public AuthorManagementController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuthorDto> createAuthor(@Valid @RequestBody AuthorCreateRequest request) {
        return ApiResponse.success(authorService.createAuthor(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<AuthorDto> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorUpdateRequest request) {
        return ApiResponse.success(authorService.updateAuthor(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ApiResponse.success("Author deleted successfully");
    }
}