package me.seyrek.library_management_system.book.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import me.seyrek.library_management_system.author.model.Author;
import me.seyrek.library_management_system.book.dto.BookSearchRequest;
import me.seyrek.library_management_system.book.model.Book;
import me.seyrek.library_management_system.category.model.Category;
import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.copy.model.CopyStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static me.seyrek.library_management_system.common.repository.SpecificationUtils.getOrCreateJoin;

/**
 * Implements JPA Specification API for dynamic query construction.
 * Allows complex filtering (AND/OR criteria) directly on the database level
 * without loading unnecessary data into memory.
 */
public final class BookSpecification {

    public static Specification<Book> withDynamicQuery(BookSearchRequest request) {
        return (root, query, cb) -> {
            List<Specification<Book>> specifications = new ArrayList<>();

            // Mode 1: Smart Search (if search is present)
            // Flexible filters for the title, ISBN and author name
            if (StringUtils.hasText(request.search())) {
                specifications.add(Specification.anyOf(
                        hasTitle(request.search()),
                        hasIsbn(request.search()),
                        hasAuthorName(request.search())
                ));
            }
            // Mode 2: Advanced Filter (if search is not present)
            // Strictly filters title, ISBN and author name
            else {
                specifications.add(hasTitle(request.title()));
                specifications.add(hasIsbn(request.isbn()));
                specifications.add(hasAuthorName(request.authorName()));
            }

            // Add other filters that apply in both modes
            // These are strict filters, always applied
            specifications.add(hasCategoryName(request.categoryName()));
            specifications.add(hasMinPrice(request.minPrice()));
            specifications.add(hasMaxPrice(request.maxPrice()));
            specifications.add(hasAvailableCopies(request.available()));

            // Combine all specifications with AND
            Specification<Book> finalSpec = Specification.allOf(specifications);

            // Determine if a distinct query is needed
            boolean smartSearchActive = StringUtils.hasText(request.search());
            boolean authorFilterActive = StringUtils.hasText(request.authorName());
            boolean availableFilterActive = request.available() != null && request.available();

            if (query != null && (smartSearchActive || authorFilterActive || availableFilterActive)) {
                query.distinct(true);
            }

            return finalSpec.toPredicate(root, query, cb);
        };
    }

    private static Specification<Book> hasIsbn(String isbn) {
        if (!StringUtils.hasText(isbn)) return null;
        return (root, query, cb) -> cb.like(root.get("isbn"), "%" + isbn + "%");
    }

    private static Specification<Book> hasTitle(String title) {
        if (!StringUtils.hasText(title)) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    private static Specification<Book> hasAuthorName(String authorName) {
        if (!StringUtils.hasText(authorName)) return null;
        return (root, query, cb) -> {
            Join<Book, Author> author = getOrCreateJoin(root, "authors", JoinType.INNER);
            return cb.like(cb.lower(author.get("name")), "%" + authorName.toLowerCase() + "%");
        };
    }

    private static Specification<Book> hasCategoryName(String categoryName) {
        if (!StringUtils.hasText(categoryName)) return null;
        return (root, query, cb) -> {
            Join<Book, Category> category = getOrCreateJoin(root, "category", JoinType.INNER);
            return cb.like(cb.lower(category.get("name")), "%" + categoryName.toLowerCase() + "%");
        };
    }

    private static Specification<Book> hasAvailableCopies(Boolean available) {
        if (available == null || !available) {
            return null;
        }
        return (root, query, cb) -> {
            Join<Book, Copy> copy = getOrCreateJoin(root, "copies", JoinType.INNER);
            return cb.equal(copy.get("status"), CopyStatus.AVAILABLE);
        };
    }

    private static Specification<Book> hasMinPrice(BigDecimal minPrice) {
        if (minPrice == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Book> hasMaxPrice(BigDecimal maxPrice) {
        if (maxPrice == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
