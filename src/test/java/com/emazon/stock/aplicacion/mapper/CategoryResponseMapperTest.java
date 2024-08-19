package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryResponseMapperTest {

    private final CategoryResponseMapper mapper = Mappers.getMapper(CategoryResponseMapper.class);

    @Test
    void shouldMapCategoryToCategoryResponse() {
        // Arrange
        Category category = new Category(1L,"Electronics","Electronic items");

        // Act
        CategoryResponse categoryResponse = mapper.toCategoryResponse(category);

        // Assert
        assertThat(categoryResponse).isNotNull();
        assertThat(categoryResponse.getId()).isEqualTo(category.getId());
        assertThat(categoryResponse.getName()).isEqualTo(category.getName());
        assertThat(categoryResponse.getDescription()).isEqualTo(category.getDescription());
    }
}