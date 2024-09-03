package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    Category toCategory(CategoryEntity category);

    @Mapping(target = "productEntities", ignore = true)
    CategoryEntity toCategoryEntity(Category category);

    List<CategoryEntity> toCategoryListEntity(List<Category> categoryList) ;

    PageStock<Category> toCategoryPageStock(Page<CategoryEntity> categoryEntityPage);

}
