package me.seyrek.library_management_system.author.dto;

public record AuthorDto(
        Long id,
        String name,
        Integer bookCount
) {
}