package me.seyrek.library_management_system.book.service;

import lombok.RequiredArgsConstructor;
import me.seyrek.library_management_system.author.model.Author;
import me.seyrek.library_management_system.author.repository.AuthorRepository;
import me.seyrek.library_management_system.book.dto.BookCreateRequest;
import me.seyrek.library_management_system.book.dto.BookDto;
import me.seyrek.library_management_system.book.dto.BookPatchRequest;
import me.seyrek.library_management_system.book.dto.BookUpdateRequest;
import me.seyrek.library_management_system.book.mapper.BookMapper;
import me.seyrek.library_management_system.book.model.Book;
import me.seyrek.library_management_system.book.repository.BookRepository;
import me.seyrek.library_management_system.category.model.Category;
import me.seyrek.library_management_system.category.repository.CategoryRepository;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.DuplicateResourceException;
import me.seyrek.library_management_system.exception.client.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toBookDto);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id, ErrorCode.BOOK_NOT_FOUND));
    }

    @Override
    public BookDto createBook(BookCreateRequest request) {
        String normalizedIsbn = normalizeIsbn(request.isbn());
        validateIsbnUniqueness(normalizedIsbn, null);

        Book book = bookMapper.fromBookCreateRequest(request);
        book.setIsbn(normalizedIsbn);

        updateBookAuthors(book, request.authorIds());
        updateBookCategory(book, request.categoryId());

        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateBook(Long id, BookUpdateRequest request) {
        Book existingBook = findBookByIdOrThrow(id);

        String normalizedIsbn = normalizeIsbn(request.isbn());
        validateIsbnUniqueness(normalizedIsbn, id);

        bookMapper.updateBookFromRequest(request, existingBook);
        existingBook.setIsbn(normalizedIsbn); // Ensure normalized ISBN is set

        updateBookAuthors(existingBook, request.authorIds());
        updateBookCategory(existingBook, request.categoryId());

        return bookMapper.toBookDto(bookRepository.save(existingBook));
    }

    @Override
    public BookDto patchBook(Long id, BookPatchRequest request) {
        Book existingBook = findBookByIdOrThrow(id);

        bookMapper.updateBookFromPatch(request, existingBook);

        Optional.ofNullable(request.isbn()).ifPresent(isbn -> {
            String normalizedIsbn = normalizeIsbn(isbn);
            validateIsbnUniqueness(normalizedIsbn, id);
            existingBook.setIsbn(normalizedIsbn);
        });

        updateBookAuthors(existingBook, request.authorIds());
        updateBookCategory(existingBook, request.categoryId());

        return bookMapper.toBookDto(bookRepository.save(existingBook));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id, ErrorCode.BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

    private void updateBookAuthors(Book book, Set<Long> authorIds) {
        if (authorIds == null) {
            return; // No change requested
        }
        if (authorIds.isEmpty()) {
            book.setAuthors(new HashSet<>()); // Clear authors
        } else {
            List<Author> foundAuthors = authorRepository.findAllById(authorIds);
            if (foundAuthors.size() != authorIds.size()) {
                throw new ResourceNotFoundException("One or more authors not found.", ErrorCode.AUTHOR_NOT_FOUND);
            }
            book.setAuthors(new HashSet<>(foundAuthors));
        }
    }

    private void updateBookCategory(Book book, Long categoryId) {
        if (categoryId == null) {
            return; // No change requested
        }
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId, ErrorCode.CATEGORY_NOT_FOUND));
        book.setCategory(category);
    }

    private void validateIsbnUniqueness(String isbn, Long currentBookId) {
        bookRepository.findByIsbn(isbn).ifPresent(book -> {
            if (currentBookId == null || !book.getId().equals(currentBookId)) {
                throw new DuplicateResourceException("Book with ISBN " + isbn + " already exists", ErrorCode.BOOK_ALREADY_EXISTS);
            }
        });
    }

    private Book findBookByIdOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id, ErrorCode.BOOK_NOT_FOUND));
    }

    private String normalizeIsbn(String isbn) {
        if (!StringUtils.hasText(isbn)) {
            return null;
        }
        return isbn.replaceAll("[^0-9X]", "");
    }
}