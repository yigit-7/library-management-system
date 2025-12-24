package me.seyrek.library_management_system.loan.repository;

import me.seyrek.library_management_system.loan.model.Loan;
import me.seyrek.library_management_system.loan.model.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {
    @Override
    @NonNull
    Page<Loan> findAll(@Nullable Specification<Loan> spec, @NonNull Pageable pageable);

    @Override
    @NonNull
    // TODO: add @NamedEntityGraph for EAGER join fetch
    @EntityGraph(attributePaths = {"user", "copy", "copy.book", "fines"})
    Optional<Loan> findById(@NonNull Long id);

    boolean existsByCopy_Book_IdAndStatus(Long id, LoanStatus loanStatus);
    long countByUserIdAndStatus(Long userId, LoanStatus status);

    // Dashboard Stats
    long countByStatus(LoanStatus status);

    /*
     * Hibernate:
     * SELECT COUNT(l)
     * FROM loan l
     * WHERE l.return_date IS NULL
     * AND l.due_date < CURRENT_TIMESTAMP
     */
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.returnDate IS NULL AND l.dueDate < CURRENT_TIMESTAMP")
    long countOverdueLoans();

    /*
     * Hibernate:
     * SELECT COUNT(l)
     * FROM loan l
     * WHERE l.return_date IS NULL
     * AND l.due_date >= CURRENT_TIMESTAMP
     * AND l.due_date <= ?
     */
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.returnDate IS NULL AND l.dueDate >= CURRENT_TIMESTAMP AND l.dueDate <= :threeDaysLater")
    long countDueSoonLoans(@Param("threeDaysLater") Instant threeDaysLater);

    List<Loan> findAllByStatusAndDueDateBefore(LoanStatus status, Instant date);

    List<Loan> findAllByStatusAndDueDateBetween(LoanStatus status, Instant start, Instant end);
}
