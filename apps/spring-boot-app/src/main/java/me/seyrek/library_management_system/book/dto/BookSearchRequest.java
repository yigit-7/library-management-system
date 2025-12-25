package me.seyrek.library_management_system.book.dto;

import java.math.BigDecimal;
import java.util.List;

public record BookSearchRequest(
        String search,

        String isbn,
        String title,
        String authorName,

        List<Long> categoryIds, // Changed from categoryId to categoryIds
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean available
) {
}
