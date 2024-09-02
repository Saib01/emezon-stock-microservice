package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.ProductRequest;
import com.emazon.stock.aplicacion.dtos.ProductResponse;
import com.emazon.stock.aplicacion.mapper.ProductRequestMapper;
import com.emazon.stock.aplicacion.mapper.ProductResponseMapper;
import com.emazon.stock.dominio.api.IProductServicePort;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static com.emazon.stock.utils.TestConstants.*;
import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductHandlerTest {
    @Mock
    private IProductServicePort productServicePort;

    @Mock
    private ProductResponseMapper productResponseMapper;

    @Mock
    private ProductRequestMapper productRequestMapper;

    @InjectMocks
    private ProductHandler productHandler;

    private Product product;
    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        category=new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        product = new Product(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION),
                new ArrayList<>(Arrays.asList(category)));
    }

    @Test
    @DisplayName("Should save Product correctly")
    void testSaveProduct() {
        ProductRequest productRequest = new ProductRequest();

        when(productRequestMapper.toProduct(productRequest)).thenReturn(product);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        productHandler.saveProduct(productRequest);

        verify(productServicePort).saveProduct(productCaptor.capture());
        assertEquals(productCaptor.getValue(), product);
    }

    @Test
    @DisplayName("Should get Product")
    void testGetProduct() {
        ProductResponse productResponse = new ProductResponse();

        when(productServicePort.getProduct(VALID_ID)).thenReturn(product);
        when(productResponseMapper.toProductResponse(product)).thenReturn(productResponse);

        ProductResponse result = productHandler.getProduct(VALID_ID);

        assertEquals(productResponse, result);
        verify(productServicePort).getProduct(VALID_ID);
        verify(productResponseMapper).toProductResponse(product);
    }

}