package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.exeption.SortDirectionIsInvalidException;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.utils.Validator;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;
    private static final String DIRECTION_ASC = "ASC";
    private static final String DIRECTION_DESC = "DESC";

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        Validator.nameAndDescription(category);
        this.categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PageStock<Category> getCategories(int page, int size, String sortBy, String sortDirection) {
        if (!(sortDirection.equalsIgnoreCase(DIRECTION_ASC) || sortDirection.equalsIgnoreCase(DIRECTION_DESC))) {
            throw new SortDirectionIsInvalidException();
        }
        return this.categoryPersistencePort.getCategories(page, size, sortBy, sortDirection);
    }

    @Override
    public Category getCategory(Long id) {
        return this.categoryPersistencePort.getCategory(id);
    }

}
