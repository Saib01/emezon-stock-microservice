package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.*;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    Category toCategory(CategoryEntity category);

    CategoryEntity toCategoryEntity(Category category);

    default PageStock<Category> toCategoryPageModel(Page<CategoryEntity> categoryEntityPage) {
        return new PageStock<>(
                categoryEntityPage.getContent().stream()
                        .map(categoryEnt -> new Category(categoryEnt.getId(), categoryEnt.getName(),
                                categoryEnt.getDescription()))
                        .toList(),
                categoryEntityPage.getTotalElements(),
                categoryEntityPage.getTotalPages(),
                categoryEntityPage.getNumber(),
                categoryEntityPage.getSize(),
                categoryEntityPage.getSort().stream()
                        .findFirst()
                        .map(Sort.Order::getProperty)
                        .orElse(""),
                categoryEntityPage.getSort().stream()
                        .findFirst()
                        .map(order -> order.getDirection().name())
                        .orElse(""));
    }
}
