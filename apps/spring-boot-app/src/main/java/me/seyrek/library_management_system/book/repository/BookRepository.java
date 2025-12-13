package me.seyrek.library_management_system.book.repository;

import me.seyrek.library_management_system.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    long countByCategoryId(Long categoryId);
    boolean existsByAuthorsId(Long authorId);
    Optional<Book> findByIsbn(String isbn);
}