package com.emazon.stock.aplicacion.mapper.category;

import com.emazon.stock.aplicacion.dtos.category.CategoryRequest;
import com.emazon.stock.dominio.modelo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_DESCRIPTION;
import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_NAME;
import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static org.assertj.core.api.Assertions.assertThat;

class CategoryRequestMapperTest {

    private final CategoryRequestMapper categoryRequestMapper = Mappers.getMapper(CategoryRequestMapper.class);
    @Test
    @DisplayName("Should map CategoryRequest to Category correctly")
    void shouldMapCategoryRequestToCategory() {
        CategoryRequest categoryRequest = new CategoryRequest(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        Category result = categoryRequestMapper.toCategory(categoryRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getName()).isEqualTo(categoryRequest.getName());
        assertThat(result.getDescription()).isEqualTo(categoryRequest.getDescription());
    }
}