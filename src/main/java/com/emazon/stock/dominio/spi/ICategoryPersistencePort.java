package com.emazon.stock.dominio.spi;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);

    PageStock<Category> getCategoriesByName(int page, int size, String sortDirection);

    Category getCategory(Long id);

    boolean findByName(String name);
}
