package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.utils.Validator;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        Validator.nameAndDescription(category);
        this.categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PageStock<Category> getCategories(String sortDirection, int page, int size) {
        Validator.sortDirection(sortDirection);
        return this.categoryPersistencePort.getCategories(sortDirection, page, size);
    }

    @Override
    public Category getCategory(Long id) {
        return this.categoryPersistencePort.getCategory(id);
    }

}
