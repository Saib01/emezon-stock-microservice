package com.emazon.stock.dominio.api;

import com.emazon.stock.dominio.modelo.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryServicePort {

    void saveCategory(Category category);
    Page<Category> getCategories(String sortDirection, int page, int size);
    Category getCategory(Long id);
}
