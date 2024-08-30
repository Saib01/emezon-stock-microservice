package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.BrandResponse;
import com.emazon.stock.dominio.modelo.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.emazon.stock.constants.TestConstants.VALID_BRAND_DESCRIPTION;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_NAME;
import static com.emazon.stock.constants.TestConstants.VALID_ID;
import static org.assertj.core.api.Assertions.assertThat;
class BrandResponseMapperTest {

    private final BrandResponseMapper brandResponseMapper = Mappers.getMapper(BrandResponseMapper.class);
    Brand brand;
    @BeforeEach
    void setUp() {
        brand = new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }

    @Test
    @DisplayName("Should map brand to brandResponse correctly")
    void shouldMapBrandToBrandResponse() {
        BrandResponse result = brandResponseMapper.toBrandResponse(brand);

        assertThat(result).isNotNull();
        assertBrandEqual(brand,result);
    }
    
    private void assertBrandEqual(Brand brand, BrandResponse brandResponse){
        assertThat(brand.getId()).isEqualTo(brandResponse.getId());
        assertThat(brand.getName()).isEqualTo(brandResponse.getName());
        assertThat(brand.getDescription()).isEqualTo(brandResponse.getDescription());
    }
}