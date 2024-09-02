package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.ProductResponse;
import com.emazon.stock.dominio.modelo.Brand;
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

class ProductResponseMapperTest {
    private final ProductResponseMapper productResponseMapper = Mappers.getMapper(ProductResponseMapper.class);
    Product product;
    Category category;
    @BeforeEach
    void setUp() {
        category=new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        product = new Product(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION),
                new ArrayList<>(Arrays.asList(category)));

    }

    @Test
    @DisplayName("Should map Product to ProductResponse correctly")
    void shouldMapProductToProductResponse() {
        ProductResponse result = productResponseMapper.toProductResponse(product);

        assertThat(result).isNotNull();
        assertProductEqual(product,result);
    }

    private void assertProductEqual(Product product, ProductResponse productResponse){
        assertThat(product.getId()).isEqualTo(productResponse.getId());
        assertThat(product.getName()).isEqualTo(productResponse.getName());
        assertThat(product.getDescription()).isEqualTo(productResponse.getDescription());
    }
}