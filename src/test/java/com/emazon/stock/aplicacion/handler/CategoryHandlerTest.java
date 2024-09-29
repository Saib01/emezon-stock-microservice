package com.emazon.stock.aplicacion.handler;

import static com.emazon.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.emazon.stock.aplicacion.dtos.category.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.category.CategoryResponse;
import com.emazon.stock.aplicacion.handler.implement.CategoryHandler;
import com.emazon.stock.aplicacion.mapper.category.CategoryRequestMapper;
import com.emazon.stock.aplicacion.mapper.category.CategoryResponseMapper;
import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.utils.PageStock;
import static com.emazon.stock.dominio.utils.Direction.ASC;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;

class CategoryHandlerTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private CategoryResponseMapper categoryResponseMapper;

    @Mock
    private CategoryRequestMapper categoryRequestMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
    }

    @Test
    @DisplayName("Should save Category correctly")
    void testSaveCategory() {
        CategoryRequest categoryRequest = new CategoryRequest();
        when(categoryRequestMapper.toCategory(categoryRequest)).thenReturn(category);

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);

        categoryHandler.saveCategory(categoryRequest);
        verify(categoryServicePort).saveCategory(categoryCaptor.capture());
        assertEquals(categoryCaptor.getValue(), category);
    }

    @Test
    @DisplayName("Should get Category")
    void testGetCategory() {
        CategoryResponse categoryResponse = new CategoryResponse();

        when(categoryServicePort.getCategory(VALID_ID)).thenReturn(category);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        CategoryResponse result = categoryHandler.getCategory(VALID_ID);

        assertEquals(categoryResponse, result);
        verify(categoryServicePort).getCategory(VALID_ID);
        verify(categoryResponseMapper).toCategoryResponse(category);
    }

    @Test
    @DisplayName("Should get Categories")
    void testGetCategories() {
        CategoryResponse categoryResponse = new CategoryResponse(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        PageStock<CategoryResponse> expectedCategoryResponsePageStock = new PageStock<>(List.of(categoryResponse),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);
        PageStock<Category> categoryPageStock = new PageStock<>(List.of(category),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);

        when(categoryServicePort.getCategoriesByName(VALID_PAGE,VALID_SIZE,ASC)).thenReturn(categoryPageStock);
        when(categoryResponseMapper.toCategoryResponsePageStock(categoryPageStock))
                .thenReturn(expectedCategoryResponsePageStock);

        PageStock<CategoryResponse> result = categoryHandler.getCategoriesByName(VALID_PAGE, VALID_SIZE,ASC);

        assertEquals(result,expectedCategoryResponsePageStock);
        verify(this.categoryServicePort,times(ONE)).getCategoriesByName(VALID_PAGE, VALID_SIZE, ASC);
    }

}