package me.seyrek.library_management_system.book.dto;

public record BookSearchRequest(
        String isbn,
        String title,
        String authorName,
        String categoryName
) {
}