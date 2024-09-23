package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.response.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import java.util.List;

import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_DESCRIPTION;
import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_NAME;
import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static com.emazon.stock.utils.TestConstants.VALID_PAGE;
import static com.emazon.stock.utils.TestConstants.VALID_SIZE;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_ELEMENTS;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_PAGES;
import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
import static com.emazon.stock.dominio.utils.Direction.ASC;
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
        PageStock<Category> categories=new PageStock<>(List.of(category),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES);
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC),PROPERTY_NAME.toLowerCase()));
        Page<CategoryResponse> result =categoryResponseMapper.toCategoryResponsePage(categories,pageable);

        assertThat(categories.getTotalPages()).isEqualTo(result.getTotalPages());
        assertThat(categories.getTotalElements().intValue()).isEqualTo(result.getTotalElements());
        assertThat(categories.getContent()).hasSameSizeAs(result.getContent());
        assertCategoryEqual(categories.getContent().get(0), result.getContent().get(0));

    }

    @Test
    @DisplayName("Should map Category to CategoryResponse correctly")
    void shouldMapCategoryToCategoryResponse() {
        CategoryResponse result = categoryResponseMapper.toCategoryResponse(category);

        assertThat(result).isNotNull();
        assertCategoryEqual(category,result);
    }


    private void assertCategoryEqual(Category category, CategoryResponse categoryResponse){
        assertThat(category.getId()).isEqualTo(categoryResponse.getId());
        assertThat(category.getName()).isEqualTo(categoryResponse.getName());
        assertThat(category.getDescription()).isEqualTo(categoryResponse.getDescription());
    }
}