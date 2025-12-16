package me.seyrek.library_management_system.author.controller;

import me.seyrek.library_management_system.author.dto.AuthorDto;
import me.seyrek.library_management_system.author.service.AuthorService;
import me.seyrek.library_management_system.common.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ApiResponse<Page<AuthorDto>> getAllAuthors(
            @RequestParam(required = false) String name,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ApiResponse.success(authorService.getAllAuthors(name, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<AuthorDto> getAuthorById(@PathVariable Long id) {
        return ApiResponse.success(authorService.getAuthorById(id));
    }
}