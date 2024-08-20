package com.emazon.stock.dominio.spi;

import com.emazon.stock.dominio.modelo.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    Page<Category> getCategories(String sortDirection, int page, int size);

    Category getCategory(Long id);
}
