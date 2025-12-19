package me.seyrek.library_management_system.fine.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import me.seyrek.library_management_system.fine.model.Fine;
import me.seyrek.library_management_system.fine.model.FineStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long>, JpaSpecificationExecutor<Fine> {
    @Override
    @NonNull
    Page<Fine> findAll(@Nullable Specification<Fine> spec, @NonNull Pageable pageable);

    @Override
    @NonNull
    // TODO: add @NamedEntityGraph for EAGER join fetch
    Optional<Fine> findById(@NonNull Long id);

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f WHERE f.user.id = :userId AND f.status = :status")
    BigDecimal sumFinesByUserIdAndStatus(@Param("userId") Long userId, @Param("status") FineStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT f FROM Fine f WHERE f.id = :id")
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    Optional<Fine> findByIdWithLock(@Param("id") Long id);
}
