package com.emazon.stock.aplicacion.handler;

import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_DESCRIPTION;
import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_NAME;
import static com.emazon.stock.utils.TestConstants.VALID_SIZE;
import static com.emazon.stock.utils.TestConstants.VALID_PAGE;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_PAGES;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_ELEMENTS;
import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.emazon.stock.aplicacion.dtos.request.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.response.CategoryResponse;
import com.emazon.stock.aplicacion.handler.implement.CategoryHandler;
import com.emazon.stock.aplicacion.mapper.CategoryRequestMapper;
import com.emazon.stock.aplicacion.mapper.CategoryResponseMapper;
import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import static com.emazon.stock.dominio.utils.Direction.ASC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


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
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC),PROPERTY_NAME.toLowerCase()));
        Page<CategoryResponse> categoryResponsePage = new PageImpl<>(List.of(categoryResponse),pageable,VALID_TOTAL_ELEMENTS);
        PageStock<Category> categoryPageStock = new PageStock<>(List.of(category), VALID_TOTAL_ELEMENTS, VALID_TOTAL_PAGES);

        when(categoryServicePort.getCategoriesByName(VALID_PAGE,VALID_SIZE,ASC)).thenReturn(categoryPageStock);
        when(categoryResponseMapper.toCategoryResponsePage(categoryPageStock,pageable))
                .thenReturn(categoryResponsePage);

        Page<CategoryResponse> result = categoryHandler.getCategoriesByName(VALID_PAGE, VALID_SIZE,ASC);

        assertEquals(VALID_TOTAL_ELEMENTS, result.getTotalElements());
        assertEquals(VALID_TOTAL_PAGES, result.getTotalPages());
        assertEquals(VALID_SIZE, result.getSize());
        assertEquals(VALID_CATEGORY_NAME, result.getContent().get(0).getName());
        assertEquals(VALID_CATEGORY_DESCRIPTION, result.getContent().get(0).getDescription());
    }


}