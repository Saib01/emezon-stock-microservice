package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CategoryEntityMapper {
    Category toCategory(CategoryEntity category);
    CategoryEntity toCategoryEntity(Category category);
}
