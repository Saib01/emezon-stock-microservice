package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.response.BrandResponse;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.PageStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.emazon.stock.utils.TestConstants.VALID_BRAND_DESCRIPTION;
import static com.emazon.stock.utils.TestConstants.VALID_BRAND_NAME;
import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static com.emazon.stock.utils.TestConstants.VALID_PAGE;
import static com.emazon.stock.utils.TestConstants.VALID_SIZE;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_ELEMENTS;
import static com.emazon.stock.utils.TestConstants.VALID_TOTAL_PAGES;
import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
import static com.emazon.stock.dominio.utils.Direction.ASC;
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
        PageStock<Brand> brands=new PageStock<>(List.of(brand),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES);
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC),PROPERTY_NAME.toLowerCase()));
        Page<BrandResponse> result =brandResponseMapper.toBrandResponsePage(brands,pageable);

        assertThat(brands.getTotalPages()).isEqualTo(result.getTotalPages());
        assertThat(brands.getTotalElements().intValue()).isEqualTo(result.getTotalElements());
        assertThat(brands.getContent()).hasSameSizeAs(result.getContent());
        assertBrandEqual(brands.getContent().get(0), result.getContent().get(0));

    }
    
    private void assertBrandEqual(Brand brand, BrandResponse brandResponse){
        assertThat(brand.getId()).isEqualTo(brandResponse.getId());
        assertThat(brand.getName()).isEqualTo(brandResponse.getName());
        assertThat(brand.getDescription()).isEqualTo(brandResponse.getDescription());
    }
}