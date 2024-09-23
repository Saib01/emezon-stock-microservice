package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.exeption.category.CategoryNotFoundException;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.utils.PageValidator;
import com.emazon.stock.dominio.utils.Validator;

import java.util.Optional;

import static com.emazon.stock.dominio.exeption.ExceptionResponse.CATEGORY_NOT_FOUND;
import static com.emazon.stock.dominio.utils.Direction.ASC;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        Validator.nameAndDescription(category);
        Validator.validateNameIsAlreadyInUse(this.categoryPersistencePort,category);
        this.categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PageStock<Category> getCategoriesByName(int page, int size, String sortDirection) {
        PageValidator.parameters(page,size,sortDirection,Category.class.getSimpleName());
        PageStock<Category> categoryPageStock =this.categoryPersistencePort.getCategoriesByName(page, size, sortDirection);
        categoryPageStock.setAscending(ASC.equalsIgnoreCase(sortDirection));
        return categoryPageStock;
    }

    @Override
    public Category getCategory(Long id) {
        return Optional.ofNullable(this.categoryPersistencePort.getCategory(id))
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND)
                );
    }
}
