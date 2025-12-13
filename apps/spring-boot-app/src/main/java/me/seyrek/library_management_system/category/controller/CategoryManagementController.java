package me.seyrek.library_management_system.category.controller;

import jakarta.validation.Valid;
import me.seyrek.library_management_system.category.dto.CategoryCreateRequest;
import me.seyrek.library_management_system.category.dto.CategoryDto;
import me.seyrek.library_management_system.category.dto.CategoryUpdateRequest;
import me.seyrek.library_management_system.category.service.CategoryService;
import me.seyrek.library_management_system.common.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/management/categories")
@PreAuthorize("hasRole('ADMIN')")
public class CategoryManagementController {

    private final CategoryService categoryService;

    public CategoryManagementController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ApiResponse<CategoryDto> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        return ApiResponse.success(categoryService.createCategory(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request) {
        return ApiResponse.success(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success("Category deleted successfully");
    }
}