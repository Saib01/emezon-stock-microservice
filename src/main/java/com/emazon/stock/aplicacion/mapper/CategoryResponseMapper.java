package com.emazon.stock.aplicacion.mapper;


import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface CategoryResponseMapper {
    CategoryResponse toCategoryResponse( Category category);
    default List<CategoryResponse> toCategoryResponseList(List<Category> categoryList){
        return categoryList.stream().map(category->{
            CategoryResponse categoryResponse=new CategoryResponse();
            categoryResponse.setId(category.getId());
            categoryResponse.setDescription(category.getDescription());
            categoryResponse.setName(category.getName());
            return categoryResponse;
        }).toList();
    }
}
