package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryHandler {

    void saveCategory(CategoryRequest categoryRequest);
    Page<CategoryResponse> getCategories(String sortDirection, int page, int size);
    CategoryResponse getCategory(Long categoryNumber);
}
