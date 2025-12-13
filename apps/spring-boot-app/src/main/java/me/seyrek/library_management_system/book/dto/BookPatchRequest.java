package me.seyrek.library_management_system.book.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

public record BookPatchRequest(
        @ISBN(message = "Invalid ISBN format")
        String isbn,

        @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
        String title,

        @Size(max = 1000, message = "Description can be at most 1000 characters")
        String description,

        @URL(message = "Invalid URL format for cover image")
        String coverImageUrl,

        Set<Long> authorIds,

        Long categoryId
) {
}