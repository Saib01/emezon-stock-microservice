package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.emazon.stock.aplicacion.util.ApplicationConstants.CONTENT;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.*;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoryEntityMapper {


    Category toCategory(CategoryEntity category);

    @Mapping(target = PRODUCT_ENTITIES, ignore = true)
    CategoryEntity toCategoryEntity(Category category);

    List<CategoryEntity> toCategoryListEntity(List<Category> categoryList);

    @Mapping(target = CONTENT, expression = EMPTY_IF_NULL_CATEGORY_ENTITY_PAGE_GET_CONTENT)
    @Mapping(target = PAGE_NUMBER, source = NUMBER)
    @Mapping(target = PAGE_SIZE, source = SIZE)
    @Mapping(target = ASCENDING, ignore = true)
    PageStock<Category> toCategoryPageStock(Page<CategoryEntity> categoryEntityPage);

    default List<Category> mapContentToEmptyIfNull(List<CategoryEntity> content) {
        return Optional.of(content.stream()
                        .map(this::toCategory)
                        .toList())
                .orElse(Collections.emptyList());
    }
}
