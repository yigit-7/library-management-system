package me.seyrek.library_management_system.category.mapper;

import me.seyrek.library_management_system.category.dto.CategoryCreateRequest;
import me.seyrek.library_management_system.category.dto.CategoryDto;
import me.seyrek.library_management_system.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
    @Mapping(target = "id", ignore = true)
    Category fromCategoryCreateRequest(CategoryCreateRequest request);
}