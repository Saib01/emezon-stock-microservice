package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.exeption.category.CategoryNameTooLongException;
import com.emazon.stock.dominio.exeption.category.CategoryNameRequiredException;
import com.emazon.stock.dominio.exeption.category.CategoryDescriptionRequiredException;
import com.emazon.stock.dominio.exeption.category.CategoryDescriptionTooLongException;
import com.emazon.stock.dominio.exeption.category.CategoryAlreadyExistException;
import com.emazon.stock.dominio.exeption.category.CategoryPageNumberIsInvalidException;
import com.emazon.stock.dominio.exeption.category.CategoryPageSizeIsInvalidException;
import com.emazon.stock.dominio.exeption.category.CategoryPageSortDirectionIsInvalidException;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
    }

    @Test
    @DisplayName("Should save the category and verify that the persistence port method is called once")
    void testSaveCategory() {
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(false);
        categoryUseCase.saveCategory(category);

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);

        verify(categoryPersistencePort, times(ONE)).saveCategory(categoryCaptor.capture());
        assertEquals(categoryCaptor.getValue(), category);

    }

    @Test
    @DisplayName("Should not save the category when the category already exists")
    void shouldNotSaveCategoryWhenCategoryAlreadyExists() {
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(true);

        assertThrows(CategoryAlreadyExistException.class, () -> categoryUseCase.saveCategory(category));

        verify(categoryPersistencePort, never()).saveCategory(category);
    }

    @Test
    @DisplayName("Should not save category when name is empty or null")
    void shouldNotSaveCategoryWhenNameIsEmptyOrNull() {
        assertAll(
                () -> {
                    category.setName(EMPTY_PROPERTY);
                    assertThrows(CategoryNameRequiredException.class, () -> categoryUseCase.saveCategory(category));
                },
                () -> {
                    category.setName(NULL_PROPERTY);
                    assertThrows(CategoryNameRequiredException.class, () -> categoryUseCase.saveCategory(category));
                }
        );
    }

    @Test
    @DisplayName("Should not save category when description is empty or null")
    void shouldNotSaveCategoryWhenDescriptionIsEmptyOrNull() {
        assertAll(
                () -> {
                    category.setDescription(EMPTY_PROPERTY);
                    assertThrows(CategoryDescriptionRequiredException.class, () -> categoryUseCase.saveCategory(category));
                },
                () -> {
                    category.setDescription(NULL_PROPERTY);
                    assertThrows(CategoryDescriptionRequiredException.class, () -> categoryUseCase.saveCategory(category));
                }
        );
    }

    @Test
    @DisplayName("Should not save category when description is too long")
    void shouldNotSaveCategoryWhenDescriptionIsTooLong() {
        category.setDescription(INVALID_CATEGORY_DESCRIPTION);
        assertThrows(CategoryDescriptionTooLongException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    @DisplayName("Should not save category when name is too long")
    void shouldNotSaveCategoryWhenNameIsTooLong() {
        category.setName(INVALID_CATEGORY_NAME);
        assertThrows(CategoryNameTooLongException.class, () -> categoryUseCase.saveCategory(category));
    }

    @Test
    @DisplayName("Should return the correct Category when fetching a category by ID")
    void shouldGetCategory() {
        when(categoryPersistencePort.getCategory(VALID_ID)).thenReturn(category);

        Category actualCategory = categoryUseCase.getCategory(VALID_ID);

        assertNotNull(actualCategory);
        assertEquals(category, actualCategory);

        verify(categoryPersistencePort, times(ONE)).getCategory(VALID_ID);
    }

    @Test
    @DisplayName("Should return a paginated page of categories")
    void shouldGetCategoryPageStock() {
        PageStock<Category> expectedCategoryPageStock = new PageStock<>(
                Collections.singletonList(category),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);

        when(categoryPersistencePort.getCategoriesByName(VALID_PAGE, VALID_SIZE, ASC))
                .thenReturn(expectedCategoryPageStock);

        PageStock<Category> actualCategoryPageStock = categoryUseCase.getCategoriesByName(VALID_PAGE, VALID_SIZE, ASC);

        assertEquals(expectedCategoryPageStock, actualCategoryPageStock);

        verify(categoryPersistencePort, times(ONE)).getCategoriesByName(VALID_PAGE, VALID_SIZE,
                ASC);
    }

    @Test
    @DisplayName("Should not return categories when the page number is invalid")
    void shouldNotGetCategoryPageStockWhenPageNumberIsInvalid() {
        assertThrows(CategoryPageNumberIsInvalidException.class,
                () -> categoryUseCase.getCategoriesByName(INVALID_PAGE, VALID_SIZE, ASC));
    }

    @Test
    @DisplayName("Should not return categories when the page size is invalid")
    void shouldNotGetCategoryPageStockWhenPageSizeIsInvalid() {
        assertThrows(CategoryPageSizeIsInvalidException.class,
                () -> categoryUseCase.getCategoriesByName(VALID_PAGE, INVALID_SIZE, ASC));
    }

    @Test
    @DisplayName("Should not return categories when the page sorting direction is invalid")
    void shouldNotGetCategoryPageStockWhenPageSortDirectionIsInvalid() {
        assertThrows(CategoryPageSortDirectionIsInvalidException.class,
                () -> categoryUseCase.getCategoriesByName(VALID_PAGE, VALID_SIZE, INVALID_SORT_DIRECTION));
    }
}
