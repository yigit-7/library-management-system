package me.seyrek.library_management_system.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

public record BookUpdateRequest(
        @NotBlank(message = "ISBN cannot be blank")
        @ISBN(message = "Invalid ISBN format")
        String isbn,

        @NotBlank(message = "Title cannot be blank")
        @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
        String title,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 5000, message = "Description can be at most 1000 characters")
        String description,

        @NotBlank(message = "Cover image URL cannot be blank")
        @URL(message = "Invalid URL format for cover image")
        String coverImageUrl,

        @NotEmpty(message = "At least one author must be specified")
        Set<Long> authorIds,

        @NotNull(message = "Category must be specified")
        Long categoryId
) {
}