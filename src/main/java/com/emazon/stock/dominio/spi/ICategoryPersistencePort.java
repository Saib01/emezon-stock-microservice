package com.emazon.stock.dominio.spi;

import com.emazon.stock.dominio.modelo.Category;

import java.util.List;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    List<Category> getAllCategory();
    Category getCategory(Long id);
}
