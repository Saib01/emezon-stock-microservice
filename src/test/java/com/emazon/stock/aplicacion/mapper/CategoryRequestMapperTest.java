package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.dominio.modelo.Category;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryRequestMapperTest {

    private final CategoryRequestMapper mapper = Mappers.getMapper(CategoryRequestMapper.class);

    @Test
    void shouldMapCategoryRequestToCategory() {
        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(1L);
        categoryRequest.setName("Electronics");
        categoryRequest.setDescription("Electronic items");

        // Act
        Category category = mapper.toCategory(categoryRequest);

        // Assert
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(categoryRequest.getId());
        assertThat(category.getName()).isEqualTo(categoryRequest.getName());
        assertThat(category.getDescription()).isEqualTo(categoryRequest.getDescription());
    }
}

//@Mapper(componentModel="spring")
//public interface CategoryEntityMapper {
//    Category ToCategory(CategoryEntity category);
//    CategoryEntity ToCategoryEntity(Category category);
//}
