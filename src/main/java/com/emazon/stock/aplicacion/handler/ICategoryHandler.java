package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.category.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.category.CategoryResponse;
import com.emazon.stock.dominio.utils.PageStock;


public interface ICategoryHandler {

    void saveCategory(CategoryRequest categoryRequest);

    PageStock<CategoryResponse> getCategoriesByName(int page, int size, String sortDirection);

    CategoryResponse getCategory(Long categoryNumber);
}
