package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.request.CategoryRequest;
import com.emazon.stock.dominio.modelo.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface CategoryRequestMapper {
    Category toCategory(CategoryRequest categoryRequest);
    List<Category> toCategoryList(List<CategoryRequest> categoryRequestList) ;
}
