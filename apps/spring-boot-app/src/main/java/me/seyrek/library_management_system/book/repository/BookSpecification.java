package me.seyrek.library_management_system.book.repository;

import jakarta.persistence.criteria.Predicate;
import me.seyrek.library_management_system.book.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// Burada WHERE sorgusu dinamik olarak şekilleniyor
public final class BookSpecification {

    public static Specification<Book> withDynamicQuery(String isbn, String title, String authorName, String categoryName) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(isbn)) {
                predicates.add(criteriaBuilder.like(root.get("isbn"), "%" + isbn + "%"));
            }
            if (StringUtils.hasText(title)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                ));
            }
            if (StringUtils.hasText(authorName)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("authors").get("name")),
                        "%" + authorName.toLowerCase() + "%"
                ));
            }
            if (StringUtils.hasText(categoryName)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("category").get("name")),
                        "%" + categoryName.toLowerCase() + "%"
                ));
            }

            if (query != null) {
                query.distinct(true);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}