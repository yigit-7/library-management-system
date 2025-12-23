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
import java.time.Instant;
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

    /*
     * Hibernate:
     * SELECT COALESCE(SUM(f.amount), 0)
     * FROM fine f
     * JOIN user u ON f.user_id = u.id
     * WHERE u.id = ?
     * AND f.status = ?
     */
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f WHERE f.user.id = :userId AND f.status = :status")
    BigDecimal sumFinesByUserIdAndStatus(@Param("userId") Long userId, @Param("status") FineStatus status);

    /*
     * Hibernate:
     * SELECT f.*
     * FROM fine f
     * WHERE f.id = ?
     * FOR UPDATE NOWAIT
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT f FROM Fine f WHERE f.id = :id")
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    Optional<Fine> findByIdWithLock(@Param("id") Long id);

    /*
     * Hibernate:
     * SELECT COALESCE(SUM(f.amount), 0)
     * FROM fine f
     * WHERE f.status = ?
     */
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f WHERE f.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") FineStatus status);

    /*
     * Hibernate:
     * SELECT COALESCE(SUM(f.amount), 0)
     * FROM fine f
     * WHERE f.status = ?
     * AND f.payment_date >= ?
     */
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f WHERE f.status = :status AND f.paymentDate >= :startDate")
    BigDecimal sumAmountByStatusAndPaymentDateAfter(@Param("status") FineStatus status, @Param("startDate") Instant startDate);
}
