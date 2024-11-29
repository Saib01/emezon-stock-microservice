package com.emazon.stock.aplicacion.mapper.category;


import com.emazon.stock.aplicacion.dtos.category.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.utils.PageStock;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static com.emazon.stock.aplicacion.util.ApplicationConstants.*;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoryResponseMapper {

    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponsesList(List<Category> categories);

    PageStock<CategoryResponse> toCategoryResponsePageStock(PageStock<Category> categoryPageStock);


    @Named(MAP_CATEGORY_WITHOUT_DESCRIPTION)
    @Mapping(target = DESCRIPTION, ignore = true)
    CategoryResponse mapCategoryWithoutDescription(Category category);

    @Named(MAP_CATEGORY_LIST_WITHOUT_DESCRIPTIONS)
    @IterableMapping(qualifiedByName = MAP_CATEGORY_WITHOUT_DESCRIPTION)
    List<CategoryResponse> mapCategoryListWithoutDescriptions(List<Category> categories);

}
