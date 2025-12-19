package me.seyrek.library_management_system.author.repository;

import me.seyrek.library_management_system.author.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// TODO: really need to add Specification?
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Page<Author> findByNameIsContainingIgnoreCase(String name, Pageable pageable);
}