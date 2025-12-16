package me.seyrek.library_management_system.book.mapper;

import me.seyrek.library_management_system.author.mapper.AuthorMapper;
import me.seyrek.library_management_system.book.annotation.ToBookEntity;
import me.seyrek.library_management_system.book.dto.BookCreateRequest;
import me.seyrek.library_management_system.book.dto.BookDto;
import me.seyrek.library_management_system.book.dto.BookPatchRequest;
import me.seyrek.library_management_system.book.dto.BookUpdateRequest;
import me.seyrek.library_management_system.book.model.Book;
import me.seyrek.library_management_system.category.mapper.CategoryMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class, CategoryMapper.class})
public interface BookMapper {
    BookDto toBookDto(Book book);

    @ToBookEntity
    Book fromBookCreateRequest(BookCreateRequest request);

    @ToBookEntity
    void updateBookFromRequest(BookUpdateRequest request, @MappingTarget Book book);

    @ToBookEntity
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchBookFromRequest(BookPatchRequest request, @MappingTarget Book book);
}