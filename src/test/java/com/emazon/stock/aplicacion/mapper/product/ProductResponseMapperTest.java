package com.emazon.stock.aplicacion.mapper.product;

import com.emazon.stock.aplicacion.dtos.brand.BrandResponse;
import com.emazon.stock.aplicacion.dtos.category.CategoryResponse;
import com.emazon.stock.aplicacion.dtos.product.ProductResponse;
import com.emazon.stock.aplicacion.mapper.brand.BrandResponseMapper;
import com.emazon.stock.aplicacion.mapper.category.CategoryResponseMapper;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.utils.PageStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;

import static com.emazon.stock.dominio.utils.DomainConstants.ZERO;
import static com.emazon.stock.utils.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import java.util.List;

class ProductResponseMapperTest {

    @InjectMocks
    private ProductResponseMapper productResponseMapper = Mappers.getMapper(ProductResponseMapper.class);

    @Mock
    private BrandResponseMapper brandResponseMapper;

    @Mock
    private CategoryResponseMapper categoryResponseMapper;

    private Product product;
    private Brand brand;
    private Category category;
    private CategoryResponse categoryResponse;
    private BrandResponse brandResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        brandResponse=new BrandResponse(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        categoryResponse = new CategoryResponse(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        category = new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        brand=new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        product = new Product(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT, VALID_PRICE,
                brand,
                new ArrayList<>(Arrays.asList(category)));
    }
    @Test
    void shouldMapProductToProductResponse() {
        when(brandResponseMapper.toBrandResponse(brand)).thenReturn(brandResponse);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(categoryResponse);
        when(categoryResponseMapper.toCategoryResponsesList(anyList())).thenReturn(List.of(categoryResponse));

        ProductResponse productResponse = productResponseMapper.toProductResponse(product);
        assertProductEqual(productResponse);
    }

    @Test
     @DisplayName("Should map PageStock<Product> to Page<ProductResponse>  Successfully")
     void toProductResponsePage() {
        categoryResponse.setDescription(null);
        brandResponse.setDescription(null);
        when(brandResponseMapper.mapBrandWithoutDescription(brand)).thenReturn(brandResponse);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(categoryResponse);
        when(categoryResponseMapper.mapCategoryListWithoutDescriptions(anyList())).thenReturn(List.of(categoryResponse));

        PageStock<Product> products=new PageStock<>(List.of(product),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);
         PageStock<ProductResponse> result =productResponseMapper.toProductResponsePageStock(products);

        assertThat(products.getTotalElements()).isEqualTo(result.getTotalElements());
        assertThat(products.getTotalPages()).isEqualTo(result.getTotalPages());
        assertThat(products.getTotalPages()).isEqualTo(result.getTotalPages());
        assertThat(products.isFirst()).isEqualTo(result.isFirst());
        assertThat(products.isLast()).isEqualTo(result.isLast());
        assertThat(products.isAscending()).isEqualTo(result.isAscending());
        assertThat(products.getContent()).hasSameSizeAs(result.getContent());
        assertProductEqual(result.getContent().get(ZERO));
     }

    private void assertProductEqual(ProductResponse productResponse) {
        assertNotNull(productResponse);
        assertThat(product.getId()).isEqualTo(productResponse.getId());
        assertThat(product.getName()).isEqualTo(productResponse.getName());
        assertThat(product.getDescription()).isEqualTo(productResponse.getDescription());
        assertEquals(product.getPrice(), productResponse.getPrice());

        assertNotNull(productResponse.getBrandResponse());
        assertEquals(brand.getName(), productResponse.getBrandResponse().getName());

        assertNotNull(productResponse.getCategoryResponseList());
        assertEquals(ONE, productResponse.getCategoryResponseList().size());
        assertEquals(category.getName(), productResponse.getCategoryResponseList().get(ZERO).getName());
    }
}