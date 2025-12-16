package me.seyrek.library_management_system.category.service;

import me.seyrek.library_management_system.book.repository.BookRepository;
import me.seyrek.library_management_system.category.dto.CategoryCreateRequest;
import me.seyrek.library_management_system.category.dto.CategoryDto;
import me.seyrek.library_management_system.category.dto.CategoryUpdateRequest;
import me.seyrek.library_management_system.category.mapper.CategoryMapper;
import me.seyrek.library_management_system.category.model.Category;
import me.seyrek.library_management_system.category.repository.CategoryRepository;
import me.seyrek.library_management_system.exception.ErrorCode;
import me.seyrek.library_management_system.exception.client.BusinessException;
import me.seyrek.library_management_system.exception.client.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, BookRepository bookRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDto> getCategories(String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return categoryRepository.findByNameIsContainingIgnoreCase(name, pageable)
                    .map(categoryMapper::toCategoryDto);
        }
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toCategoryDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toCategoryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id, ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public CategoryDto createCategory(CategoryCreateRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new BusinessException("Category with name '" + request.name() + "' already exists.", ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        Category category = categoryMapper.fromCategoryCreateRequest(request);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryUpdateRequest request) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id, ErrorCode.CATEGORY_NOT_FOUND));
        existingCategory.setName(request.name());
        return categoryMapper.toCategoryDto(categoryRepository.save(existingCategory));
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id, ErrorCode.CATEGORY_NOT_FOUND);
        }
        if (bookRepository.countByCategoryId(id) > 0) {
            throw new BusinessException("Cannot delete category with associated books.", ErrorCode.DATA_INTEGRITY_VIOLATION);
        }
        categoryRepository.deleteById(id);
    }
}