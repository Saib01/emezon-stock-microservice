package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.dominio.modelo.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CategoryRequestMapper {
    Category toCategory(CategoryRequest categoryRequest);
}
