package me.seyrek.library_management_system.category.controller;

import lombok.RequiredArgsConstructor;
import me.seyrek.library_management_system.category.dto.CategoryDto;
import me.seyrek.library_management_system.category.service.CategoryService;
import me.seyrek.library_management_system.common.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<Page<CategoryDto>> getCategories(
            @RequestParam(required = false) String name,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ApiResponse.success(categoryService.getCategories(name, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ApiResponse.success(categoryService.getCategoryById(id));
    }
}