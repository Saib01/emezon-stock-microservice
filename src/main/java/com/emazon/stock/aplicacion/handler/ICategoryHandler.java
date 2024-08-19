package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;

import java.util.List;

public interface ICategoryHandler {

    void saveCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategory();
    CategoryResponse getCategory(Long categoryNumber);
}
