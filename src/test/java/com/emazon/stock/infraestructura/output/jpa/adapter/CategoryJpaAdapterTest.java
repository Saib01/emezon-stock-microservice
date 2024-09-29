package com.emazon.stock.infraestructura.output.jpa.adapter;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import com.emazon.stock.infraestructura.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.utils.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
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

        ArgumentCaptor<CategoryEntity> categoryEntityCaptor= ArgumentCaptor.forClass(CategoryEntity.class);

        verify(categoryEntityMapper, times(ONE)).toCategoryEntity(category);
        verify(categoryRepository, times(ONE)).save(categoryEntityCaptor.capture());
        assertEquals(categoryEntityCaptor.getValue(), categoryEntity);
    }

    @Test
    @DisplayName("Should retrieve categories by name with pagination and sorting")
    void getCategoriesByName() {
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC), PROPERTY_NAME.toLowerCase()));
        Page<CategoryEntity> categoryEntityPage = new PageImpl<>(List.of(categoryEntity), pageable, VALID_TOTAL_ELEMENTS);
        PageStock<Category> expectedCategoryPageStock = new PageStock<>(List.of(category),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryEntityPage);
        when(categoryEntityMapper.toCategoryPageStock(categoryEntityPage)).thenReturn(expectedCategoryPageStock);

        PageStock<Category> actualCategoryPageStock = categoryJpaAdapter.getCategoriesByName(VALID_PAGE, VALID_SIZE, ASC);

        assertEquals(expectedCategoryPageStock,actualCategoryPageStock);
        verify(categoryRepository, times(ONE)).findAll(pageable);
        verify(categoryEntityMapper, times(ONE)).toCategoryPageStock(categoryEntityPage);
    }
    @Test
    @DisplayName("Should retrieve a category by its ID")
    void getCategory() {

        when(categoryRepository.findById(VALID_ID)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);
        when(categoryRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        Category result = categoryJpaAdapter.getCategory(VALID_ID);
        assertThat(result).isEqualTo(category);
        assertNull(categoryJpaAdapter.getCategory(INVALID_ID));

        verify(categoryRepository, times(ONE)).findById(VALID_ID);
        verify(categoryEntityMapper, times(ONE)).toCategory(categoryEntity);
        verify(categoryRepository, times(ONE)).findById(INVALID_ID);
    }

    @Test
    @DisplayName("Should retrieve category by name")
    void findByName() {
        when(categoryRepository.existsByName(VALID_CATEGORY_NAME)).thenReturn(true);
        categoryJpaAdapter.existsByName(VALID_CATEGORY_NAME);

        verify((categoryRepository), times(ONE)).existsByName(VALID_CATEGORY_NAME);
    }

    @Test
    @DisplayName("Should return true when category exists by id")
    void shouldReturnTrueWhenCategoryExistsById() {
        when(categoryRepository.existsById(VALID_ID)).thenReturn(true);

        boolean result = categoryJpaAdapter.existsById(VALID_ID);
        assertTrue(result);
    }

}