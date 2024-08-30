package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.BrandRequest;
import com.emazon.stock.aplicacion.dtos.BrandResponse;
import com.emazon.stock.aplicacion.mapper.BrandRequestMapper;
import com.emazon.stock.aplicacion.mapper.BrandResponseMapper;
import com.emazon.stock.dominio.api.IBrandServicePort;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.PageStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.emazon.stock.constants.TestConstants.VALID_ID;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_DESCRIPTION;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_NAME;
import static com.emazon.stock.constants.TestConstants.VALID_SIZE;
import static com.emazon.stock.constants.TestConstants.VALID_PAGE;
import static com.emazon.stock.constants.TestConstants.VALID_TOTAL_PAGES;
import static com.emazon.stock.constants.TestConstants.VALID_TOTAL_ELEMENTS;
import static com.emazon.stock.dominio.utils.ConstantsDominio.PROPERTY_NAME;
import static com.emazon.stock.dominio.utils.ConstantsDominio.DIRECTION_ASC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrandHandlerTest {


    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private BrandResponseMapper brandResponseMapper;

    @Mock
    private BrandRequestMapper brandRequestMapper;

    @InjectMocks
    private BrandHandler brandHandler;

    private Brand brand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        brand = new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }

    @Test
    @DisplayName("Should save brand correctly")
    void testSaveBrand() {
        BrandRequest brandRequest = new BrandRequest();
        when(brandRequestMapper.toBrand(brandRequest)).thenReturn(brand);
        brandHandler.saveBrand(brandRequest);
        verify(brandServicePort).saveBrand(brand);
    }

    @Test
    @DisplayName("Should get brand")
    void testGetBrand() {
        BrandResponse brandResponse = new BrandResponse();
        when(brandServicePort.getBrand(VALID_ID)).thenReturn(brand);
        when(brandResponseMapper.toBrandResponse(brand)).thenReturn(brandResponse);
        BrandResponse result = brandHandler.getBrand(VALID_ID);
        assertEquals(brandResponse, result);
        verify(brandServicePort).getBrand(VALID_ID);
        verify(brandResponseMapper).toBrandResponse(brand);
    }

    @Test
    @DisplayName("Should get Brands")
    void testGetBrands() {
        BrandResponse brandResponse = new BrandResponse(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(DIRECTION_ASC),PROPERTY_NAME.toLowerCase()));
        Page<BrandResponse> brandResponsePage = new PageImpl<>(List.of(brandResponse),pageable,VALID_TOTAL_ELEMENTS);
        PageStock<Brand> brandPageStock = new PageStock<>(List.of(brand), VALID_TOTAL_ELEMENTS, VALID_TOTAL_PAGES);

        when(brandServicePort.getBrandsByName(VALID_PAGE,VALID_SIZE, DIRECTION_ASC)).thenReturn(brandPageStock);
        when(brandResponseMapper.toBrandResponsePage(brandPageStock,pageable))
                .thenReturn(brandResponsePage);

        Page<BrandResponse> result = brandHandler.getBrandsByName(VALID_PAGE, VALID_SIZE, DIRECTION_ASC);

        assertEquals(VALID_TOTAL_ELEMENTS, result.getTotalElements());
        assertEquals(VALID_TOTAL_PAGES, result.getTotalPages());
        assertEquals(VALID_SIZE, result.getSize());
        assertEquals(VALID_BRAND_NAME, result.getContent().get(0).getName());
        assertEquals(VALID_BRAND_DESCRIPTION, result.getContent().get(0).getDescription());
    }
    
}