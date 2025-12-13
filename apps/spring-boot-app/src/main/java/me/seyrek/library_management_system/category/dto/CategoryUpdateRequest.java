package me.seyrek.library_management_system.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateRequest(
        @NotBlank(message = "Category name cannot be blank")
        @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
        String name
) {
}