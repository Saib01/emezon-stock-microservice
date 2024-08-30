package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.BrandRequest;
import com.emazon.stock.aplicacion.dtos.BrandResponse;
import com.emazon.stock.aplicacion.mapper.BrandRequestMapper;
import com.emazon.stock.aplicacion.mapper.BrandResponseMapper;
import com.emazon.stock.dominio.api.IBrandServicePort;
import com.emazon.stock.dominio.modelo.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.emazon.stock.constants.TestConstants.VALID_ID;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_DESCRIPTION;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_NAME;
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
    
}