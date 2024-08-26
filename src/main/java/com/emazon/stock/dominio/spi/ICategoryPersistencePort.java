package com.emazon.stock.dominio.spi;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);

    PageStock<Category> getCategories(String sortDirection, int page, int size);

    Category getCategory(Long id);
}
