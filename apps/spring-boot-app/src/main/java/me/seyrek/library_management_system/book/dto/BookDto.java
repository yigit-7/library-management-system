package me.seyrek.library_management_system.book.dto;

import me.seyrek.library_management_system.author.dto.AuthorDto;
import me.seyrek.library_management_system.category.dto.CategoryDto;

import java.util.Set;

public record BookDto(
        Long id,
        String isbn,
        String title,
        String description,
        String coverImageUrl,
        Set<AuthorDto> authors,
        CategoryDto category
) {
}