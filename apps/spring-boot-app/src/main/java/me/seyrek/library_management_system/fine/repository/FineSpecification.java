package me.seyrek.library_management_system.fine.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import me.seyrek.library_management_system.book.model.Book;
import me.seyrek.library_management_system.fine.dto.FineSearchRequest;
import me.seyrek.library_management_system.fine.model.Fine;
import me.seyrek.library_management_system.fine.model.FineStatus;
import me.seyrek.library_management_system.loan.model.Loan;
import me.seyrek.library_management_system.user.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import static me.seyrek.library_management_system.common.repository.SpecificationUtils.getOrCreateJoin;

/**
 * Implements JPA Specification API for dynamic query construction.
 * Allows complex filtering (AND/OR criteria) directly on the database level
 * without loading unnecessary data into memory.
 */
public final class FineSpecification {

    public static Specification<Fine> withDynamicQuery(FineSearchRequest request) {
        return Specification.allOf(Arrays.asList(
                hasUserId(request.userId()),
                hasUserEmail(request.userEmail()),
                hasLoanId(request.loanId()),
                hasBookId(request.bookId()),
                hasStatus(request.status()),
                hasMinAmount(request.minAmount()),
                hasMaxAmount(request.maxAmount()),
                dateGreaterThanOrEqualTo("fineDate", request.fineDateStart()),
                dateLessThanOrEqualTo("fineDate", request.fineDateEnd()),
                dateGreaterThanOrEqualTo("paymentDate", request.paymentDateStart()),
                dateLessThanOrEqualTo("paymentDate", request.paymentDateEnd())
        ));
    }

    // -----------------------------------------------------------------
    // USER SPECIFICATIONS
    // -----------------------------------------------------------------
    private static Specification<Fine> hasUserId(Long userId) {
        if (userId == null) return null;
        return (root, query, cb) -> {
            Join<Fine, User> user = getOrCreateJoin(root, "user", JoinType.INNER);
            return cb.equal(user.get("id"), userId);
        };
    }

    private static Specification<Fine> hasUserEmail(String email) {
        if (!StringUtils.hasText(email)) return null;
        return (root, query, cb) -> {
            Join<Fine, User> user = getOrCreateJoin(root, "user", JoinType.INNER);
            return cb.like(cb.lower(user.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    // -----------------------------------------------------------------
    // LOAN & BOOK SPECIFICATIONS
    // -----------------------------------------------------------------
    private static Specification<Fine> hasLoanId(Long loanId) {
        if (loanId == null) return null;
        return (root, query, cb) -> {
            Join<Fine, Loan> loan = getOrCreateJoin(root, "loan", JoinType.INNER);
            return cb.equal(loan.get("id"), loanId);
        };
    }

    private static Specification<Fine> hasBookId(Long bookId) {
        if (bookId == null) return null;
        return (root, query, cb) -> {
            Join<Fine, Loan> loan = getOrCreateJoin(root, "loan", JoinType.INNER);
            Join<Loan, Book> book = getOrCreateJoin(loan, "copy", JoinType.INNER).join("book", JoinType.INNER);
            return cb.equal(book.get("id"), bookId);
        };
    }

    // -----------------------------------------------------------------
    // FINE STATUS & AMOUNT SPECIFICATIONS
    // -----------------------------------------------------------------
    private static Specification<Fine> hasStatus(FineStatus status) {
        if (status == null) return null;
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    private static Specification<Fine> hasMinAmount(BigDecimal minAmount) {
        if (minAmount == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("amount"), minAmount);
    }

    private static Specification<Fine> hasMaxAmount(BigDecimal maxAmount) {
        if (maxAmount == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("amount"), maxAmount);
    }

    // -----------------------------------------------------------------
    // DATE SPECIFICATIONS
    // -----------------------------------------------------------------
    private static Specification<Fine> dateGreaterThanOrEqualTo(String attribute, Instant date) {
        if (date == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), date);
    }

    private static Specification<Fine> dateLessThanOrEqualTo(String attribute, Instant date) {
        if (date == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), date);
    }
}
