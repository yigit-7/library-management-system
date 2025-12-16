package me.seyrek.library_management_system.book.service;

import me.seyrek.library_management_system.book.dto.BookCreateRequest;
import me.seyrek.library_management_system.book.dto.BookDto;
import me.seyrek.library_management_system.book.dto.BookPatchRequest;
import me.seyrek.library_management_system.book.dto.BookSearchRequest;
import me.seyrek.library_management_system.book.dto.BookUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDto> getAllBooks(BookSearchRequest request, Pageable pageable);
    BookDto getBookById(Long id);
    BookDto createBook(BookCreateRequest request);
    BookDto updateBook(Long id, BookUpdateRequest request);
    BookDto patchBook(Long id, BookPatchRequest request);
    void deleteBook(Long id);
}