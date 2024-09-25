package com.emazon.stock.aplicacion.mapper.category;

import com.emazon.stock.aplicacion.dtos.category.CategoryRequest;
import com.emazon.stock.dominio.modelo.Category;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoryRequestMapper {
    Category toCategory(CategoryRequest categoryRequest);

    List<Category> toCategoryList(List<CategoryRequest> categoryRequestList);
}
