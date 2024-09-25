package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.category.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.product.ProductRequest;
import com.emazon.stock.aplicacion.dtos.brand.BrandResponse;
import com.emazon.stock.aplicacion.dtos.category.CategoryResponse;
import com.emazon.stock.aplicacion.dtos.product.ProductResponse;
import com.emazon.stock.aplicacion.handler.IProductHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.utils.TestConstants.*;
import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductHandler productHandler;

    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should Save Product Successfully")
    void testSaveProduct() throws Exception {
        doNothing().when(productHandler).saveProduct(any(ProductRequest.class));

        CategoryRequest categoryRequest=new CategoryRequest(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        ProductRequest productRequest = new ProductRequest(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                VALID_ID,
                new ArrayList<>(Arrays.asList(categoryRequest.getId())));

        mockMvc.perform(post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());

        ArgumentCaptor<ProductRequest> productRequestCaptor=ArgumentCaptor.forClass(ProductRequest.class);

        verify(productHandler, times(1)).saveProduct(productRequestCaptor.capture());
        assertThat(productRequestCaptor.getValue().getId()).isEqualTo(productRequest.getId());
        assertThat(productRequestCaptor.getValue().getName()).isEqualTo(productRequest.getName());
        assertThat(productRequestCaptor.getValue().getDescription()).isEqualTo(productRequest.getDescription());
        assertEquals(productRequestCaptor.getValue().getAmount(),productRequest.getAmount());
        assertEquals(productRequestCaptor.getValue().getPrice(),productRequest.getPrice());
        assertEquals(productRequestCaptor.getValue().getBrandId(),productRequest.getBrandId());
        assertEquals(productRequestCaptor.getValue().getCategoryIdsList(),productRequest.getCategoryIdsList()
        );
    }

    @Test
    @DisplayName("Should Retrieve Product By ID")
    void testGetProductById() throws Exception {

        CategoryResponse categoryResponse=new CategoryResponse(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        ProductResponse productResponse= new ProductResponse(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                new BrandResponse(VALID_ID,VALID_BRAND_NAME,VALID_BRAND_DESCRIPTION),
                new ArrayList<>(Arrays.asList(categoryResponse)));

        when(productHandler.getProduct(VALID_ID)).thenReturn(productResponse);

        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID))
                .andExpect(jsonPath("$.name").value(VALID_PRODUCT_NAME))
                .andExpect(jsonPath("$.description").value(VALID_PRODUCT_DESCRIPTION))
                .andExpect(jsonPath("$.price").value(VALID_PRICE))
                .andExpect(jsonPath("$.amount").value(VALID_AMOUNT))
                .andExpect(jsonPath("$.brandResponse.id").value(VALID_ID))
                .andExpect(jsonPath("$.brandResponse.name").value(VALID_BRAND_NAME))
                .andExpect(jsonPath("$.brandResponse.description").value(VALID_BRAND_DESCRIPTION))
                .andExpect(jsonPath("$.categoryResponseList[0].id").value(VALID_ID))
                .andExpect(jsonPath("$.categoryResponseList[0].name").value(VALID_CATEGORY_NAME))
                .andExpect(jsonPath("$.categoryResponseList[0].description").value(VALID_CATEGORY_DESCRIPTION));
    }


    @Test
    @DisplayName("Should Return List of Products")
    void testGetProducts() throws Exception {

        CategoryResponse categoryResponse=new CategoryResponse(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        ProductResponse productResponse= new ProductResponse(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                new BrandResponse(VALID_ID,VALID_BRAND_NAME,VALID_BRAND_DESCRIPTION),
                new ArrayList<>(Arrays.asList(categoryResponse)));

        Pageable pageable = PageRequest.of(VALID_PAGE,VALID_SIZE);
        Page<ProductResponse> productResponsePage = new PageImpl<>(List.of(productResponse), pageable, VALID_TOTAL_ELEMENTS);
        when(productHandler.getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,CATEGORY_PROPERTY_NAME, ASC)).thenReturn(productResponsePage);
        mockMvc.perform(get("/product")
                        .param("sortBy", CATEGORY_PROPERTY_NAME)
                        .param("sortDirection", ASC)
                        .param("page", Integer.toString(VALID_PAGE))
                        .param("size", Integer.toString(VALID_SIZE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(VALID_ID))
                .andExpect(jsonPath("$.content[0].name").value(VALID_PRODUCT_NAME))
                .andExpect(jsonPath("$.content[0].description").value(VALID_PRODUCT_DESCRIPTION))
                .andExpect(jsonPath("$.content[0].brandResponse.id").value(VALID_ID))
                .andExpect(jsonPath("$.content[0].brandResponse.name").value(VALID_BRAND_NAME))
                .andExpect(jsonPath("$.content[0].categoryResponseList[0].id").value(VALID_ID))
                .andExpect(jsonPath("$.content[0].categoryResponseList[0].name").value(VALID_CATEGORY_NAME));
    }
}