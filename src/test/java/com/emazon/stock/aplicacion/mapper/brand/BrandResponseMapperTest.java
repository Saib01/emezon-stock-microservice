package com.emazon.stock.aplicacion.mapper.brand;

import com.emazon.stock.aplicacion.dtos.brand.BrandResponse;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.utils.PageStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.emazon.stock.dominio.utils.DomainConstants.ZERO;
import static com.emazon.stock.utils.TestConstants.VALID_BRAND_DESCRIPTION;
import static com.emazon.stock.utils.TestConstants.VALID_BRAND_NAME;
import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static com.emazon.stock.utils.TestConstants.VALID_PAGE;
import static com.emazon.stock.utils.TestConstants.VALID_SIZE;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_ELEMENTS;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_PAGES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    @DisplayName("Should map brand to brandResponse correctly without description")
    void shouldMapBrandToBrandResponseWithoutDescription() {
        BrandResponse result = brandResponseMapper.mapBrandWithoutDescription(brand);

        assertThat(result).isNotNull();
        assertNull(result.getDescription());
    }
    @Test
    @DisplayName("Should map List<Brand> to List<BrandResponse> correctly")
    void toBrandResponsesList() {
        List<Brand> brandList=List.of(brand);
        List<BrandResponse> result=brandResponseMapper.toBrandResponsesList(brandList);

        assertThat(brandList).hasSameSizeAs(result);
        assertBrandEqual(brandList.get(0),result.get(0));
    }

    @Test
    @DisplayName("Should map PageStock<brand> to Page<brandResponse>  Successfully")
    void toBrandResponsePage() {
        PageStock<Brand> brands=new PageStock<>(List.of(brand),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);
        PageStock<BrandResponse> result =brandResponseMapper.toBrandResponsePageStock(brands);
        assertThat(brands.getTotalElements()).isEqualTo(result.getTotalElements());
        assertThat(brands.getTotalPages()).isEqualTo(result.getTotalPages());
        assertThat(brands.getTotalPages()).isEqualTo(result.getTotalPages());
        assertThat(brands.isFirst()).isEqualTo(result.isFirst());
        assertThat(brands.isLast()).isEqualTo(result.isLast());
        assertThat(brands.isAscending()).isEqualTo(result.isAscending());
        assertThat(brands.getContent()).hasSameSizeAs(result.getContent());
        assertBrandEqual(brands.getContent().get(ZERO), result.getContent().get(ZERO));

    }
    private void assertBrandEqual(Brand brand, BrandResponse brandResponse){
        assertThat(brand.getId()).isEqualTo(brandResponse.getId());
        assertThat(brand.getName()).isEqualTo(brandResponse.getName());
        assertThat(brand.getDescription()).isEqualTo(brandResponse.getDescription());
    }
}