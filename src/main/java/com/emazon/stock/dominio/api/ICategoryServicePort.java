package com.emazon.stock.dominio.api;

import com.emazon.stock.dominio.modelo.Category;

import java.util.List;

public interface ICategoryServicePort {

    void saveCategory(Category category);
    List<Category> getAllCategory();
    Category getCategory(Long id);
}
