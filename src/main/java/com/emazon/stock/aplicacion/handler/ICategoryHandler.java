package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.request.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.response.CategoryResponse;
import com.emazon.stock.dominio.modelo.PageStock;


public interface ICategoryHandler {

    void saveCategory(CategoryRequest categoryRequest);
    PageStock<CategoryResponse> getCategoriesByName(int page, int size, String sortDirection);
    CategoryResponse getCategory(Long categoryNumber);
}
