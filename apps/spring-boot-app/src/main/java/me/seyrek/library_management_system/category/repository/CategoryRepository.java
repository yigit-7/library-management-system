package me.seyrek.library_management_system.category.repository;

import me.seyrek.library_management_system.category.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// TODO: really need to add Specification?
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    Optional<Category> findByNameIgnoreCase(String name);
    Page<Category> findByNameIsContainingIgnoreCase(String name, Pageable pageable);
}