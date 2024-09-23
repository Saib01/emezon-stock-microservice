package com.emazon.stock.aplicacion.mapper;


import com.emazon.stock.aplicacion.dtos.response.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


import java.util.List;

@Mapper(componentModel = "Spring")
public interface CategoryResponseMapper {
    CategoryResponse toCategoryResponse( Category category);

    List<CategoryResponse> toCategoryResponsesList(List<Category> categories) ;
    PageStock<CategoryResponse> toCategoryResponsePageStock(PageStock<Category> categoryPageStock);

    @Named("mapWithoutDescription")
    @Mapping(target = "description", ignore = true)
    CategoryResponse mapWithoutDescription(Category category);

    @Named("mapWithoutCategoryDescription")
    @IterableMapping(qualifiedByName = "mapWithoutDescription")
    List<CategoryResponse> mapWithoutCategoryDescription(List<Category> categories);
}
