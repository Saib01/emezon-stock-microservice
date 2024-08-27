package com.emazon.stock.aplicacion.handler;

import static com.emazon.stock.dominio.constants.GlobalConstants.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.aplicacion.mapper.CategoryRequestMapper;
import com.emazon.stock.aplicacion.mapper.CategoryResponseMapper;
import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Collections;

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

    @Test
    @DisplayName("Should get Categories")
    void testGetCategories() {
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortDirection = "ASC";
        CategoryResponse categoryResponse = new CategoryResponse(VALID_ID, VALID_NAME, VALID_DESCRIPTION);
        Page<CategoryResponse> categoryResponsePage = new PageImpl<>(Collections.singletonList(categoryResponse));
        PageStock<Category> categoryPageStock = new PageStock<>(Collections.singletonList(category), 1, 1);

        when(categoryServicePort.getCategories(page, size, sortBy, sortDirection)).thenReturn(categoryPageStock);
        when(categoryResponseMapper.toCategoryResponsePage(any(PageStock.class), any(Pageable.class)))
                .thenReturn(categoryResponsePage);

        Page<CategoryResponse> result = categoryHandler.getCategories(page, size, sortBy, sortDirection);

        assertEquals(VALID_ID, result.getTotalElements());
        assertEquals(VALID_NAME, result.getContent().get(0).getName());
        assertEquals(VALID_DESCRIPTION, result.getContent().get(0).getDescription());
    }
}