package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.category.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.product.ProductRequest;
import com.emazon.stock.aplicacion.mapper.product.ProductRequestMapper;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;

import static com.emazon.stock.utils.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductRequestMapperTest {
    private final ProductRequestMapper productRequestMapper = Mappers.getMapper(ProductRequestMapper.class);

    ProductRequest productRequest;
    CategoryRequest categoryRequest;
    @BeforeEach
    void setUp() {
        categoryRequest=new CategoryRequest(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        productRequest = new ProductRequest(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                VALID_ID,
                new ArrayList<>(Arrays.asList(categoryRequest.getId())));

    }
    @Test
    @DisplayName("Should map ProductRequest to Product correctly")
    void shouldMapProductRequestToProduct() {
        Product result = productRequestMapper.toProduct(productRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productRequest.getId());
        assertThat(result.getName()).isEqualTo(productRequest.getName());
        assertThat(result.getDescription()).isEqualTo(productRequest.getDescription());
        assertEquals(result.getAmount(),productRequest.getAmount());
        assertEquals(result.getPrice(),productRequest.getPrice());
        assertEquals(result.getBrand().getId(),productRequest.getBrandId());
        assertEquals(
                result.getCategoryList().stream()
                        .map(Category::getId)
                        .toList(),
                productRequest.getCategoryIdsList()
        );
    }
}