package me.seyrek.library_management_system.author.service;

import me.seyrek.library_management_system.author.dto.AuthorCreateRequest;
import me.seyrek.library_management_system.author.dto.AuthorDto;
import me.seyrek.library_management_system.author.dto.AuthorUpdateRequest;
import me.seyrek.library_management_system.author.mapper.AuthorMapper;
import me.seyrek.library_management_system.author.model.Author;
import me.seyrek.library_management_system.author.repository.AuthorRepository;
import me.seyrek.library_management_system.book.repository.BookRepository;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.BusinessException;
import me.seyrek.library_management_system.exception.client.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorDto> getAllAuthors(String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return authorRepository.findByNameIsContainingIgnoreCase(name, pageable)
                    .map(authorMapper::toAuthorDto);
        }
        return authorRepository.findAll(pageable)
                .map(authorMapper::toAuthorDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toAuthorDto)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id, ErrorCode.AUTHOR_NOT_FOUND));
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateRequest request) {
        Author author = authorMapper.fromAuthorCreateRequest(request);
        return authorMapper.toAuthorDto(authorRepository.save(author));
    }

    @Override
    public AuthorDto updateAuthor(Long id, AuthorUpdateRequest request) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id, ErrorCode.AUTHOR_NOT_FOUND));
        existingAuthor.setName(request.name());
        return authorMapper.toAuthorDto(authorRepository.save(existingAuthor));
    }

    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id, ErrorCode.AUTHOR_NOT_FOUND);
        }
        if (bookRepository.existsByAuthorsId(id)) {
            throw new BusinessException("Cannot delete author with associated books.", ErrorCode.DATA_INTEGRITY_VIOLATION);
        }
        authorRepository.deleteById(id);
    }
}