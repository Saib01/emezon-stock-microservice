package com.emazon.stock.dominio.api;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;

public interface ICategoryServicePort {

    void saveCategory(Category category);

    PageStock<Category> getCategories(String sortDirection, int page, int size);

    Category getCategory(Long id);
}
