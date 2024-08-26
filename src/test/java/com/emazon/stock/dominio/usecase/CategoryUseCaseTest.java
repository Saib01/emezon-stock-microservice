package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.exeption.*;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static com.emazon.stock.dominio.constants.GlobalConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category(VALID_ID, VALID_NAME, VALID_DESCRIPTION);
    }

    @Test
    @DisplayName("Should save the category and verify that the persistence port method is called once")
    void testSaveCategory() {
        categoryUseCase.saveCategory(category);
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    @DisplayName("Should not save category when name is empty")
    void shouldNotSaveCategoryWhenNameIsEmpty() {
        category.setName(EMPTY_PROPERTY);
        assertThrows(CategoryNameRequiredException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
    }

    @Test
    @DisplayName("Should not save category when name is null")
    void shouldNotSaveCategoryWhenNameIsNull() {
        category.setName(NULL_PROPERTY);
        assertThrows(CategoryNameRequiredException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
    }

    @Test
    @DisplayName("Should not save category when description is null")
    void shouldNotSaveCategoryWhenDescriptionIsNull() {
        category.setDescription(NULL_PROPERTY);
        assertThrows(CategoryDescriptionRequiredException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
    }

    @DisplayName("Should not save category when description is empty")
    @Test
    void shouldNotSaveCategoryWhenDescriptionIsEmpty() {
        category.setDescription(EMPTY_PROPERTY);
        assertThrows(CategoryDescriptionRequiredException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
    }

    @Test
    @DisplayName("Should not save category when description is too long")
    void shouldNotSaveCategoryWhenDescriptionIsTooLong() {
        category.setDescription(INVALID_DESCRIPTION);
        assertThrows(CategoryDescriptionTooLongException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
    }

    @Test
    @DisplayName("Should not save category when name is too long")
    void shouldNotSaveCategoryWhenNameIsTooLong() {
        category.setName(INVALID_NAME);
        assertThrows(CategoryNameTooLongException.class, () -> {
            categoryUseCase.saveCategory(category);
        });
    }

    @Test
    @DisplayName("Should return the correct Category when fetching a category by ID")
    void shouldGetCategory() {
        when(categoryPersistencePort.getCategory(VALID_ID)).thenReturn(category);
        Category actualCategory = categoryUseCase.getCategory(VALID_ID);
        assertNotNull(actualCategory);
        assertEquals(category.getName(), actualCategory.getName());
        assertEquals(category.getDescription(), actualCategory.getDescription());
        assertEquals(category.getId(), actualCategory.getId());
        verify(categoryPersistencePort, times(1)).getCategory(VALID_ID);
    }

    @Test
    @DisplayName("Should return a paginated page of categories")
    void shouldGetCategoryPageStock() {
        int page = 0;
        int size = 2;
        String sortDirection = "ASC";
        PageStock<Category> expectedCategoryPageStock = new PageStock<Category>(
                Arrays.asList(category, new Category(2L, "Books", "Books and literature")),
                2, 1, page, size, "name", sortDirection);
        when(this.categoryPersistencePort.getCategories(sortDirection, page, size))
                .thenReturn(expectedCategoryPageStock);
        PageStock<Category> actualCategoryPageStock = this.categoryPersistencePort.getCategories(sortDirection, page,
                size);
        assertEquals(expectedCategoryPageStock, actualCategoryPageStock);
        verify(categoryPersistencePort, times(1)).getCategories("ASC", 0, 2);
    }

    @Test
    @DisplayName("Should not return a paginated page of categories")
    void shouldNotGetCategoryPageStock() {
        int page = 0;
        int size = 2;
        String sortDirection = "AaSC";
        assertThrows(SortDirectionIsInvalidException.class, () -> {
            categoryUseCase.getCategories(sortDirection, page, size);
        });
    }
}
