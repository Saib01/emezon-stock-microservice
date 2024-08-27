package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.emazon.stock.dominio.constants.GlobalConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperTest {

    private final CategoryRequestMapper categoryRequestMapper = Mappers.getMapper(CategoryRequestMapper.class);
    private final CategoryResponseMapper categoryResponseMapper = Mappers.getMapper(CategoryResponseMapper.class);

    @Test
    @DisplayName("Should map CategoryRequest to Category correctly")
    void shouldMapCategoryRequestToCategory() {
        CategoryRequest categoryRequest = new CategoryRequest(VALID_ID, VALID_NAME, VALID_DESCRIPTION);
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(categoryRequest.getId());
        assertThat(category.getName()).isEqualTo(categoryRequest.getName());
        assertThat(category.getDescription()).isEqualTo(categoryRequest.getDescription());
    }

    @Test
    @DisplayName("Should map Category to CategoryResponse correctly")
    void shouldMapCategoryToCategoryResponse() {
        Category category = new Category(VALID_ID, VALID_NAME, VALID_DESCRIPTION);
        CategoryResponse categoryResponse = categoryResponseMapper.toCategoryResponse(category);
        assertThat(categoryResponse).isNotNull();
        assertThat(categoryResponse.getId()).isEqualTo(category.getId());
        assertThat(categoryResponse.getName()).isEqualTo(category.getName());
        assertThat(categoryResponse.getDescription()).isEqualTo(category.getDescription());
    }
}