package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.BrandEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.*;

import java.util.List;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
import static com.emazon.stock.utils.TestConstants.*;
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
    @Test
    @DisplayName("Should map Page<BrandEntity> to PageStock<Brand>  Successfully")
    void toPageStock() {
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC),PROPERTY_NAME.toLowerCase()));
        Page<BrandEntity> brandEntityPage=new PageImpl<>(List.of(brandEntity),pageable,VALID_TOTAL_ELEMENTS);
        PageStock<Brand> result=brandEntityMapper.toBrandPageStock(brandEntityPage);

        assertThat(result.getTotalPages()).isEqualTo(brandEntityPage.getTotalPages());
        assertThat(result.getTotalElements().intValue()).isEqualTo(brandEntityPage.getTotalElements());
        assertThat(result.getContent()).hasSameSizeAs(brandEntityPage.getContent());
        assertBrandEqual(result.getContent().get(0),brandEntityPage.getContent().get(0));
    }
    private void assertBrandEqual(Brand brand, BrandEntity brandEntity){
        assertThat(brand.getId()).isEqualTo(brandEntity.getId());
        assertThat(brand.getName()).isEqualTo(brandEntity.getName());
        assertThat(brand.getDescription()).isEqualTo(brandEntity.getDescription());
    }
}