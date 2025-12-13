package me.seyrek.library_management_system.category.service;

import me.seyrek.library_management_system.category.dto.CategoryCreateRequest;
import me.seyrek.library_management_system.category.dto.CategoryDto;
import me.seyrek.library_management_system.category.dto.CategoryUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDto> getAllCategories(Pageable pageable);
    CategoryDto getCategoryById(Long id);
    CategoryDto createCategory(CategoryCreateRequest request);
    CategoryDto updateCategory(Long id, CategoryUpdateRequest request);
    void deleteCategory(Long id);
}