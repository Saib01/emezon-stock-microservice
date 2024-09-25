package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.infraestructura.output.jpa.entity.BrandEntity;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import com.emazon.stock.infraestructura.output.jpa.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
import static com.emazon.stock.utils.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductEntityMapperTest {

    @Autowired
    private ProductEntityMapper productEntityMapper ;
    @Autowired
    private CategoryEntityMapper categoryEntityMapper ;
    @Autowired
    private BrandEntityMapper brandEntityMapper ;
    ProductEntity productEntity;
    CategoryEntity categoryEntity;
    Product product;
    Category category;

    @BeforeEach
    void setUp() {
        categoryEntity=new CategoryEntity(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        category=new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);

        productEntity = new ProductEntity(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                new BrandEntity(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION),
                new ArrayList<>(Arrays.asList(categoryEntity)));

        product= new Product(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION),
                new ArrayList<>(Arrays.asList(category)));
    }
    @Test
    @DisplayName("Should map ProductEntity to Product correctly")
    void toProduct() {
        Product result= productEntityMapper.toProduct(productEntity);

        assertProductEqual(result,productEntity);
    }
    @Test
    @DisplayName("Should map Product to ProductEntity correctly")
    void toProductEntity() {
        ProductEntity result= productEntityMapper.toProductEntity(product);

        assertProductEqual(product,result);
    }

    @Test
    @DisplayName("Should map Page<ProductEntity> to PageStock<Product>  Successfully")
    void toPageStock() {
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC),PROPERTY_NAME.toLowerCase()));
        Page<ProductEntity> productEntityPage=new PageImpl<>(List.of(productEntity),pageable,VALID_TOTAL_ELEMENTS);
        PageStock<Product> result=productEntityMapper.toProductPageStock(productEntityPage);

        assertThat(result.getTotalPages()).isEqualTo(productEntityPage.getTotalPages());
        assertThat(result.getTotalElements().intValue()).isEqualTo(productEntityPage.getTotalElements());
        assertThat(result.getContent()).hasSameSizeAs(productEntityPage.getContent());
        assertProductEqual(result.getContent().get(0),productEntityPage.getContent().get(0));
    }
    private void assertProductEqual(Product product,ProductEntity productEntity){
        assertThat(product.getId()).isEqualTo(productEntity.getId());
        assertThat(product.getName()).isEqualTo(productEntity.getName());
        assertThat(product.getDescription()).isEqualTo(productEntity.getDescription());
    }



}
