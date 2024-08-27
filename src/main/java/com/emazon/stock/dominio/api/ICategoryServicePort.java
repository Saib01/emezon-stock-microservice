package com.emazon.stock.dominio.api;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;

public interface ICategoryServicePort {

    void saveCategory(Category category);

    PageStock<Category> getCategories(int page,int size,String sortBy,String sortDirection);

    Category getCategory(Long id);
}
