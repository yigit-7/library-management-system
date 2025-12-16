package me.seyrek.library_management_system.copy.repository;

import jakarta.persistence.criteria.Predicate;
import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.copy.model.CopyStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class CopySpecification {

    public static Specification<Copy> withDynamicQuery(String barcode, String isbn, Long bookId, CopyStatus copyStatus) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(barcode)) {
                predicates.add(criteriaBuilder.like(root.get("barcode"), "%" + barcode + "%"));
            }
            if (StringUtils.hasText(isbn)) {
                predicates.add(criteriaBuilder.like(root.join("book").get("isbn"), "%" + isbn + "%"));
            }
            
            if (bookId != null) {
                predicates.add(criteriaBuilder.equal(root.get("book").get("id"), bookId));
            }

            if (copyStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), copyStatus));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
