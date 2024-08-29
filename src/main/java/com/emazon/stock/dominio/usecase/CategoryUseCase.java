package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.utils.PageValidator;
import com.emazon.stock.dominio.utils.Validator;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        Validator.nameAndDescription(category);
        Validator.isNameAlreadyInUse(this.categoryPersistencePort.findByName(category.getName()),category);
        this.categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PageStock<Category> getCategoriesByName(int page, int size, String sortDirection) {
        PageValidator.parameters(page,size,sortDirection,Category.class.getSimpleName());
        return this.categoryPersistencePort.getCategoriesByName(page, size, sortDirection);
    }

    @Override
    public Category getCategory(Long id) {
        return this.categoryPersistencePort.getCategory(id);
    }

}
