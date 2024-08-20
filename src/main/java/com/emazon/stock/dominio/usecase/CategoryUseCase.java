package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import org.springframework.data.domain.Page;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        this.categoryPersistencePort.saveCategory(category);
    }

    @Override
    public Page<Category> getCategories(String sortDirection,int page,int size) {
        return this.categoryPersistencePort.getCategories(sortDirection, page, size);
    }
    @Override
    public Category getCategory(Long id) {
        return this.categoryPersistencePort.getCategory(id);
    }

}
