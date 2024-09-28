package com.emazon.stock.aplicacion.mapper.category;

import com.emazon.stock.aplicacion.dtos.category.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.utils.PageStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.emazon.stock.dominio.utils.DomainConstants.ZERO;
import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_DESCRIPTION;
import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_NAME;
import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static com.emazon.stock.utils.TestConstants.VALID_PAGE;
import static com.emazon.stock.utils.TestConstants.VALID_SIZE;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_ELEMENTS;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_PAGES;
import static org.assertj.core.api.Assertions.assertThat;

class CategoryResponseMapperTest {

    private final CategoryResponseMapper categoryResponseMapper = Mappers.getMapper(CategoryResponseMapper.class);
    Category category;
    @BeforeEach
    void setUp() {
        category = new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
    }
    @Test
    @DisplayName("Should map List<Category> to List<CategoryResponse> correctly")
    void toCategoryResponsesList() {
        List<Category> categoryList=List.of(category);
        List<CategoryResponse> result=categoryResponseMapper.toCategoryResponsesList(categoryList);

        assertThat(categoryList).hasSameSizeAs(result);
        assertCategoryEqual(categoryList.get(0),result.get(0));
    }

    @Test
    @DisplayName("Should map PageStock<Category> to Page<CategoryResponse>  Successfully")
    void toCategoryResponsePage() {
        PageStock<Category> categories=new PageStock<>(List.of(category),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);
        PageStock<CategoryResponse> result =categoryResponseMapper.toCategoryResponsePageStock(categories);

        assertThat(categories.getTotalElements()).isEqualTo(result.getTotalElements());
        assertThat(categories.getTotalPages()).isEqualTo(result.getTotalPages());
        assertThat(categories.getTotalPages()).isEqualTo(result.getTotalPages());
        assertThat(categories.isFirst()).isEqualTo(result.isFirst());
        assertThat(categories.isLast()).isEqualTo(result.isLast());
        assertThat(categories.isAscending()).isEqualTo(result.isAscending());
        assertThat(categories.getContent()).hasSameSizeAs(result.getContent());
        assertCategoryEqual(categories.getContent().get(ZERO), result.getContent().get(ZERO));

    }
    @Test
    @DisplayName("Should map Category to CategoryResponse correctly")
    void shouldMapCategoryToCategoryResponse() {
        CategoryResponse result = categoryResponseMapper.toCategoryResponse(category);

        assertThat(result).isNotNull();
        assertCategoryEqual(category,result);
    }
    @Test
    @DisplayName("Should map Category to CategoryResponse correctly without description")
    void shouldMapCategoryToCategoryResponseWithoutDescription() {
        CategoryResponse result = categoryResponseMapper.mapCategoryWithoutDescription(category);

        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isNull();
    }

    @Test
    @DisplayName("Should map List<Category> to List<CategoryResponse> correctly without description")
    void toCategoryResponsesListWithOutDescription() {
        List<Category> categoryList=List.of(category);
        List<CategoryResponse> result=categoryResponseMapper.mapCategoryListWithoutDescriptions(categoryList);

        assertThat(categoryList).hasSameSizeAs(result);
        result.stream().forEach(categoryResponse -> {
            assertThat(categoryResponse.getDescription()).isNull();
        });
    }
    private void assertCategoryEqual(Category category, CategoryResponse categoryResponse){
        assertThat(category.getId()).isEqualTo(categoryResponse.getId());
        assertThat(category.getName()).isEqualTo(categoryResponse.getName());
        assertThat(category.getDescription()).isEqualTo(categoryResponse.getDescription());
    }
}