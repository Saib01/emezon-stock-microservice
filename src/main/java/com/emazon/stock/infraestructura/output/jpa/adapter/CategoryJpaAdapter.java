package com.emazon.stock.infraestructura.output.jpa.adapter;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.infraestructura.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {
        this.categoryRepository.save(
                categoryEntityMapper.toCategoryEntity(category));
    }

    @Override
    public PageStock<Category> getCategoriesByName(int page, int size, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDirection), PROPERTY_NAME.toLowerCase()));
        return categoryEntityMapper.toCategoryPageStock(this.categoryRepository.findAll(pageable));
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .map(categoryEntityMapper::toCategory)
                .orElse(null);
    }

    @Override
    public boolean existsByName(String name) {
        return this.categoryRepository.existsByName(name);
    }

    @Override
    public boolean existsById(Long id) {
        return this.categoryRepository.existsById(id);
    }
}