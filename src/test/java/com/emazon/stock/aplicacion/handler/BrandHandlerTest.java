package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.brand.BrandRequest;
import com.emazon.stock.aplicacion.dtos.brand.BrandResponse;
import com.emazon.stock.aplicacion.handler.implement.BrandHandler;
import com.emazon.stock.aplicacion.mapper.brand.BrandRequestMapper;
import com.emazon.stock.aplicacion.mapper.brand.BrandResponseMapper;
import com.emazon.stock.dominio.api.IBrandServicePort;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.utils.PageStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

        ArgumentCaptor<Brand> brandRequestCaptor = ArgumentCaptor.forClass(Brand.class);

        verify(brandServicePort).saveBrand(brandRequestCaptor.capture());
        assertEquals(brandRequestCaptor.getValue(), brand);
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
        PageStock<BrandResponse> expectedBrandResponsePageStock = new PageStock<>(List.of(brandResponse),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);
        PageStock<Brand> brandPageStock = new PageStock<>(List.of(brand),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);

        when(brandServicePort.getBrandsByName(VALID_PAGE, VALID_SIZE, ASC)).thenReturn(brandPageStock);
        when(brandResponseMapper.toBrandResponsePageStock(brandPageStock))
                .thenReturn(expectedBrandResponsePageStock);

        PageStock<BrandResponse> result = brandHandler.getBrandsByName(VALID_PAGE, VALID_SIZE, ASC);
        assertEquals(result,expectedBrandResponsePageStock);
        verify(this.brandServicePort,times(ONE)).getBrandsByName(VALID_PAGE, VALID_SIZE, ASC);
    }
}