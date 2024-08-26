package com.emazon.stock.aplicacion.handler;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.aplicacion.mapper.CategoryRequestMapper;
import com.emazon.stock.aplicacion.mapper.CategoryResponseMapper;
import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        category = new Category(1L, "Electronics", "Devices and gadgets.");
    }

    @Test
    @DisplayName("Should save Category correctly")
    void testSaveCategory() {
        CategoryRequest categoryRequest = new CategoryRequest();
        when(categoryRequestMapper.toCategory(categoryRequest)).thenReturn(category);
        categoryHandler.saveCategory(categoryRequest);
        verify(categoryServicePort).saveCategory(category);
    }

    @Test
    @DisplayName("Should get Category")
    void testGetCategory() {
        Long categoryNumber = 1L;
        CategoryResponse categoryResponse = new CategoryResponse();
        when(categoryServicePort.getCategory(categoryNumber)).thenReturn(category);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(categoryResponse);
        CategoryResponse result = categoryHandler.getCategory(categoryNumber);
        assertEquals(categoryResponse, result);
        verify(categoryServicePort).getCategory(categoryNumber);
        verify(categoryResponseMapper).toCategoryResponse(category);
    }

}