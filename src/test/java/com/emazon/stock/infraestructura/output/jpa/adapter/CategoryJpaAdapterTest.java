package com.emazon.stock.infraestructura.output.jpa.adapter;

import com.emazon.stock.dominio.exeption.CategoryNotFoundException;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import com.emazon.stock.infraestructura.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.emazon.stock.constants.TestConstants.*;
import static com.emazon.stock.dominio.utils.ConstantsDominio.PROPERTY_NAME;
import static com.emazon.stock.dominio.utils.PageValidator.DIRECTION_ASC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryJpaAdapterTest {

    @Mock
    private CategoryEntityMapper categoryEntityMapper ;
    @InjectMocks
    private  CategoryJpaAdapter categoryJpaAdapter;
    @Mock
    private  ICategoryRepository categoryRepository;
    CategoryEntity categoryEntity;
    Category category;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryEntity=new CategoryEntity(VALID_ID,VALID_CATEGORY_NAME,VALID_CATEGORY_DESCRIPTION);
        category=new Category(VALID_ID,VALID_CATEGORY_NAME,VALID_CATEGORY_DESCRIPTION);
    }
    @Test
    @DisplayName("Should save the category and verify that the category repository method is called once")
    void saveCategory() {

        when(categoryEntityMapper.toCategoryEntity(category)).thenReturn(categoryEntity);
        categoryJpaAdapter.saveCategory(category);

        verify(categoryEntityMapper, times(1)).toCategoryEntity(category);
        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    @DisplayName("Should retrieve categories by name with pagination and sorting")
    void getCategoriesByName() {
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(DIRECTION_ASC), PROPERTY_NAME.toLowerCase()));
        Page<CategoryEntity> categoryEntityPage = new PageImpl<>(List.of(categoryEntity), pageable, VALID_TOTAL_ELEMENTS);
        PageStock<Category> expectedCategoryPageStock = new PageStock<>(List.of(category),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryEntityPage);
        when(categoryEntityMapper.toPageStock(categoryEntityPage)).thenReturn(expectedCategoryPageStock);

        PageStock<Category> actualCategoryPageStock = categoryJpaAdapter.getCategoriesByName(VALID_PAGE, VALID_SIZE, DIRECTION_ASC);

        assertEquals(expectedCategoryPageStock,actualCategoryPageStock);
        verify(categoryRepository, times(1)).findAll(pageable);
        verify(categoryEntityMapper, times(1)).toPageStock(categoryEntityPage);
    }

    @Test
    @DisplayName("Should retrieve a category by its ID")
    void getCategory() {

        when(categoryRepository.findById(VALID_ID)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        Category result = categoryJpaAdapter.getCategory(VALID_ID);
        assertThat(result).isEqualTo(category);

        verify(categoryRepository, times(1)).findById(VALID_ID);
        verify(categoryEntityMapper, times(1)).toCategory(categoryEntity);
    }

    @Test
    @DisplayName("Should throw CategoryNotFoundException when category is not found")
    void getCategoryWhenNotFoundShouldThrowException() {
        when(categoryRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryJpaAdapter.getCategory(INVALID_ID);
        });
    }

    @Test
    @DisplayName("Should retrieve category by name")
    void findByName() {
        when(categoryRepository.existsByName(VALID_CATEGORY_NAME)).thenReturn(true);
        categoryJpaAdapter.findByName(VALID_CATEGORY_NAME);

        verify((categoryRepository), times(1)).existsByName(VALID_CATEGORY_NAME);
    }


}