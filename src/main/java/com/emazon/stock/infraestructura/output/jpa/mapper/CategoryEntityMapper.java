package com.emazon.stock.infraestructura.output.jpa.mapper;


import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel="spring")
public interface CategoryEntityMapper {
    Category toCategory(CategoryEntity category);
    CategoryEntity toCategoryEntity(Category category);

    default Page<Category> toCategoryPage(Page<CategoryEntity> categoryEntity) {
        return new PageImpl<>(
                categoryEntity.stream()
                        .map(categoryEnt -> {
                            Category category = new Category(categoryEnt.getId(),categoryEnt.getName(),categoryEnt.getDescription());
                            return category;
                        })
                        .toList(),
                categoryEntity.getPageable(),
                categoryEntity.getTotalElements()
        );
    }
}
