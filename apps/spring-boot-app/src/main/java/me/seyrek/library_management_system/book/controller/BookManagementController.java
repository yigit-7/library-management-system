package me.seyrek.library_management_system.book.controller;

import jakarta.validation.Valid;
import me.seyrek.library_management_system.book.dto.BookCreateRequest;
import me.seyrek.library_management_system.book.dto.BookDto;
import me.seyrek.library_management_system.book.dto.BookPatchRequest;
import me.seyrek.library_management_system.book.dto.BookUpdateRequest;
import me.seyrek.library_management_system.book.service.BookService;
import me.seyrek.library_management_system.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/management/books")
public class BookManagementController {

    private final BookService bookService;

    public BookManagementController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BookDto> createBook(@Valid @RequestBody BookCreateRequest request) {
        return ApiResponse.success(bookService.createBook(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BookDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest request) {
        return ApiResponse.success(bookService.updateBook(id, request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BookDto> patchBook(@PathVariable Long id, @Valid @RequestBody BookPatchRequest request) {
        return ApiResponse.success(bookService.patchBook(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ApiResponse.success("Book with ID " + id + " has been successfully deleted");
    }
}