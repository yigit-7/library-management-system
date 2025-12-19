package me.seyrek.library_management_system.book.dto;

import java.math.BigDecimal;

public record BookSearchRequest(
        // TODO: maybe add category name too?
        String search,

        String isbn,
        String title,
        String authorName,

        String categoryName,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean available
) {
}
