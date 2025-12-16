package me.seyrek.library_management_system.copy.mapper;

import me.seyrek.library_management_system.book.mapper.BookMapper;
import me.seyrek.library_management_system.copy.dto.CopyCreateRequest;
import me.seyrek.library_management_system.copy.dto.CopyDto;
import me.seyrek.library_management_system.copy.dto.CopyPatchRequest;
import me.seyrek.library_management_system.copy.dto.CopyUpdateRequest;
import me.seyrek.library_management_system.copy.model.Copy;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface CopyMapper {

    CopyDto toCopyDto(Copy copy);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", ignore = true) // Handled in service
    @Mapping(target = "status", ignore = true) // Defaulted in entity
    Copy fromCopyCreateRequest(CopyCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", ignore = true) // Handled in service
    @Mapping(target = "barcode", ignore = true) // Handled in service
    @Mapping(target = "status", ignore = true) // Handled in service
    void updateCopyFromRequest(CopyUpdateRequest request, @MappingTarget Copy copy);

    @Mapping(target = "book", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "barcode", ignore = true) // Handled in service
    @Mapping(target = "status", ignore = true) // Handled in service
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchCopyFromRequest(CopyPatchRequest request, @MappingTarget Copy copy);
}