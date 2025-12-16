package me.seyrek.library_management_system.author.service;

import me.seyrek.library_management_system.author.dto.AuthorCreateRequest;
import me.seyrek.library_management_system.author.dto.AuthorDto;
import me.seyrek.library_management_system.author.dto.AuthorUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    Page<AuthorDto> getAllAuthors(String name, Pageable pageable);
    AuthorDto getAuthorById(Long id);
    AuthorDto createAuthor(AuthorCreateRequest request);
    AuthorDto updateAuthor(Long id, AuthorUpdateRequest request);
    void deleteAuthor(Long id);
}