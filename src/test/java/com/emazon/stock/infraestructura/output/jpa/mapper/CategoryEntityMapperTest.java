package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import java.util.List;

import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_DESCRIPTION;
import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_NAME;
import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static com.emazon.stock.utils.TestConstants.VALID_PAGE;
import static com.emazon.stock.utils.TestConstants.VALID_SIZE;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_ELEMENTS;
import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
import static com.emazon.stock.dominio.utils.Direction.ASC;
import static org.assertj.core.api.Assertions.assertThat;

class CategoryEntityMapperTest {

    private final CategoryEntityMapper categoryEntityMapper = Mappers.getMapper(CategoryEntityMapper.class);
    CategoryEntity categoryEntity;


    @BeforeEach
    void setUp() {
        categoryEntity = new CategoryEntity(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
    }
    @Test
    @DisplayName("Should map CategoryEntity to Category correctly")
    void toCategory() {
        Category result= categoryEntityMapper.toCategory(categoryEntity);

        assertCategoryEqual(result,categoryEntity);
    }

    @Test
    @DisplayName("Should map Category to CategoryEntity correctly")
    void toCategoryEntity() {
        Category category=new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        CategoryEntity result= categoryEntityMapper.toCategoryEntity(category);

        assertCategoryEqual(category,result);
    }

    @Test
    @DisplayName("Should map Page<CategoryEntity> to PageStock<Category>  Successfully")
    void toPageStock() {
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC),PROPERTY_NAME.toLowerCase()));
        Page<CategoryEntity> categoryEntityPage=new PageImpl<>(List.of(categoryEntity),pageable,VALID_TOTAL_ELEMENTS);
        PageStock<Category> result=categoryEntityMapper.toPageStock(categoryEntityPage);

        assertThat(result.getTotalPages()).isEqualTo(categoryEntityPage.getTotalPages());
        assertThat(result.getTotalElements().intValue()).isEqualTo(categoryEntityPage.getTotalElements());
        assertThat(result.getContent()).hasSameSizeAs(categoryEntityPage.getContent());
        assertCategoryEqual(result.getContent().get(0),categoryEntityPage.getContent().get(0));
    }

    private void assertCategoryEqual(Category category,CategoryEntity categoryEntity){
        assertThat(category.getId()).isEqualTo(categoryEntity.getId());
        assertThat(category.getName()).isEqualTo(categoryEntity.getName());
        assertThat(category.getDescription()).isEqualTo(categoryEntity.getDescription());
    }



}