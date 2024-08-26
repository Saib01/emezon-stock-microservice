package com.emazon.stock.infraestructura.adapter;

import com.emazon.stock.dominio.exeption.*;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.infraestructura.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private static final String CATEGORY_PROPERTY_ORDER = "name";

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistException();
        }
        this.categoryRepository.save(
                categoryEntityMapper.toCategoryEntity(category));
    }

    @Override
    public PageStock<Category> getCategories(String sortDirection, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), CATEGORY_PROPERTY_ORDER);
        return categoryEntityMapper.toCategoryPageModel(
                this.categoryRepository.findAll(
                        PageRequest.of(page,
                                size,
                                sort)));
    }

    @Override
    public Category getCategory(Long id) {
        return categoryEntityMapper.toCategory(
                categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new));
    }
}