package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.response.BrandResponse;
import com.emazon.stock.aplicacion.dtos.response.CategoryResponse;
import com.emazon.stock.aplicacion.dtos.request.ProductRequest;
import com.emazon.stock.aplicacion.dtos.response.ProductResponse;
import com.emazon.stock.aplicacion.handler.implement.ProductHandler;
import com.emazon.stock.aplicacion.mapper.ProductRequestMapper;
import com.emazon.stock.aplicacion.mapper.ProductResponseMapper;
import com.emazon.stock.dominio.api.IProductServicePort;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Category category = new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        product = new Product(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION),
                new ArrayList<>(List.of(category)));
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

    @Test
    @DisplayName("Should get Products")
    void testGetProducts() {
        ProductResponse productResponse =new ProductResponse(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                new BrandResponse(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION),
                List.of(
                        new CategoryResponse(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION)
                )
        );
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC),PROPERTY_NAME.toLowerCase()));
        Page<ProductResponse> productResponsePage = new PageImpl<>(List.of(productResponse),pageable,VALID_TOTAL_ELEMENTS);
        PageStock<Product> productPageStock = new PageStock<>(List.of(product), VALID_TOTAL_ELEMENTS, VALID_TOTAL_PAGES);

        when(productServicePort.getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,CATEGORY_PROPERTY_NAME, ASC)).thenReturn(productPageStock);
        when(productResponseMapper.toProductResponsePage(productPageStock,pageable))
                .thenReturn(productResponsePage);

        Page<ProductResponse> result = productHandler.getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,CATEGORY_PROPERTY_NAME, ASC);

        assertEquals(VALID_TOTAL_ELEMENTS, result.getTotalElements());
        assertEquals(VALID_TOTAL_PAGES, result.getTotalPages());
        assertEquals(VALID_SIZE, result.getSize());
        assertEquals(VALID_PRODUCT_NAME, result.getContent().get(0).getName());
        assertEquals(VALID_PRODUCT_DESCRIPTION, result.getContent().get(0).getDescription());
    }

    @Test
    @DisplayName("Should add supply to product correctly")
    void shouldAddSupplyCorrectly() {

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Integer> supplyCaptor = ArgumentCaptor.forClass(Integer.class);

        productHandler.addSupply(VALID_ID, VALID_AMOUNT);


        verify(productServicePort).addSupply(idCaptor.capture(), supplyCaptor.capture());

        assertEquals(VALID_ID, idCaptor.getValue());
        assertEquals(VALID_AMOUNT, supplyCaptor.getValue());
    }

    @Test
    @DisplayName("Should return true when product limit per category is valid")
    void shouldReturnTrueWhenProductLimitPerCategoryIsValid() {
        when(productServicePort.validateMaxProductPerCategory(VALID_LIST_PRODUCTS_IDS)).thenReturn(true);

        boolean result = productHandler.validateMaxProductPerCategory(VALID_LIST_PRODUCTS_IDS);

        assertTrue(result);
    }

}