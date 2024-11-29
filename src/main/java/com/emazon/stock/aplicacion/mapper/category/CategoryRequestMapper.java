package com.emazon.stock.aplicacion.mapper.category;

import com.emazon.stock.aplicacion.dtos.category.CategoryRequest;
import com.emazon.stock.dominio.modelo.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import static com.emazon.stock.dominio.utils.DomainConstants.ID;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoryRequestMapper {
    @Mapping(target = ID, ignore = true)
    Category toCategory(CategoryRequest categoryRequest);
}
