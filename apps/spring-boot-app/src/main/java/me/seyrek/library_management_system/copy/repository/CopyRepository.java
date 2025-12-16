package me.seyrek.library_management_system.copy.repository;

import me.seyrek.library_management_system.copy.model.Copy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long>, JpaSpecificationExecutor<Copy> {
    Optional<Copy> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);
    boolean existsByBookId(Long bookId);
    Page<Copy> findByBookId(Long bookId, Pageable pageable);
}