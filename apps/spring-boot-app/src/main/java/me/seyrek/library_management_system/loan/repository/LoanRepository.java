package me.seyrek.library_management_system.loan.repository;

import me.seyrek.library_management_system.loan.model.Loan;
import me.seyrek.library_management_system.loan.model.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

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
}
