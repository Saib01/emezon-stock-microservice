package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import org.springframework.data.domain.Page;


public interface ICategoryHandler {

    void saveCategory(CategoryRequest categoryRequest);
    Page<CategoryResponse> getCategories(int page,int size,String sortBy,String sortDirection);
    CategoryResponse getCategory(Long categoryNumber);
}
