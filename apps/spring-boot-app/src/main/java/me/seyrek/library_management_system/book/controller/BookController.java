package me.seyrek.library_management_system.book.controller;

import me.seyrek.library_management_system.book.dto.BookDto;
import me.seyrek.library_management_system.book.service.BookService;
import me.seyrek.library_management_system.common.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ApiResponse<Page<BookDto>> getAllBooks(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ApiResponse.success(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<BookDto> getBookById(@PathVariable Long id) {
        return ApiResponse.success(bookService.getBookById(id));
    }
}