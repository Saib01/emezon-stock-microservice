package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.request.BrandRequest;
import com.emazon.stock.dominio.modelo.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.emazon.stock.utils.TestConstants.VALID_BRAND_DESCRIPTION;
import static com.emazon.stock.utils.TestConstants.VALID_BRAND_NAME;
import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static org.assertj.core.api.Assertions.assertThat;

class BrandRequestMapperTest {

    private final BrandRequestMapper brandRequestMapper = Mappers.getMapper(BrandRequestMapper.class);
    @Test
    @DisplayName("Should map BrandRequest to Brand correctly")
    void shouldMapBrandRequestToBrand() {
        BrandRequest brandRequest = new BrandRequest(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        Brand result = brandRequestMapper.toBrand(brandRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(brandRequest.getId());
        assertThat(result.getName()).isEqualTo(brandRequest.getName());
        assertThat(result.getDescription()).isEqualTo(brandRequest.getDescription());
    }
}