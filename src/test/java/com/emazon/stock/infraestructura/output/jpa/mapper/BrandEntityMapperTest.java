package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.infraestructura.output.jpa.entity.BrandEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.emazon.stock.constants.TestConstants.VALID_BRAND_DESCRIPTION;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_NAME;
import static com.emazon.stock.constants.TestConstants.VALID_ID;
import static org.assertj.core.api.Assertions.assertThat;

class BrandEntityMapperTest {


    private final BrandEntityMapper brandEntityMapper = Mappers.getMapper(BrandEntityMapper.class);
    BrandEntity brandEntity;


    @BeforeEach
    void setUp() {
        brandEntity = new BrandEntity(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }
    @Test
    @DisplayName("Should map BrandEntity to Brand correctly")
    void toBrand() {
        Brand result= brandEntityMapper.toBrand(brandEntity);

        assertBrandEqual(result,brandEntity);
    }

    @Test
    @DisplayName("Should map Brand to BrandEntity correctly")
    void toBrandEntity() {
        Brand brand=new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        BrandEntity result= brandEntityMapper.toBrandEntity(brand);

        assertBrandEqual(brand,result);
    }

    private void assertBrandEqual(Brand brand, BrandEntity brandEntity){
        assertThat(brand.getId()).isEqualTo(brandEntity.getId());
        assertThat(brand.getName()).isEqualTo(brandEntity.getName());
        assertThat(brand.getDescription()).isEqualTo(brandEntity.getDescription());
    }
}