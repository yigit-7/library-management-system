package me.seyrek.library_management_system.category.controller;

import me.seyrek.library_management_system.category.dto.CategoryDto;
import me.seyrek.library_management_system.category.service.CategoryService;
import me.seyrek.library_management_system.common.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ApiResponse<Page<CategoryDto>> getAllCategories(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ApiResponse.success(categoryService.getAllCategories(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ApiResponse.success(categoryService.getCategoryById(id));
    }
}