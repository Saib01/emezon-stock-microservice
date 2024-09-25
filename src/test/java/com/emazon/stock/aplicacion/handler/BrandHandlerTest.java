package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.brand.BrandRequest;
import com.emazon.stock.aplicacion.dtos.brand.BrandResponse;
import com.emazon.stock.aplicacion.handler.implement.BrandHandler;
import com.emazon.stock.aplicacion.mapper.brand.BrandRequestMapper;
import com.emazon.stock.aplicacion.mapper.brand.BrandResponseMapper;
import com.emazon.stock.dominio.api.IBrandServicePort;
import com.emazon.stock.dominio.modelo.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.emazon.stock.utils.TestConstants.VALID_ID;
import static com.emazon.stock.utils.TestConstants.VALID_BRAND_DESCRIPTION;
import static com.emazon.stock.utils.TestConstants.VALID_BRAND_NAME;
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
/*
    @Test
    @DisplayName("Should get Brands")
    void testGetBrands() {
        BrandResponse brandResponse = new BrandResponse(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC),PROPERTY_NAME.toLowerCase()));
        Page<BrandResponse> brandResponsePage = new PageImpl<>(List.of(brandResponse),pageable,VALID_TOTAL_ELEMENTS);
        PageStock<Brand> brandPageStock = new PageStock<>(List.of(brand), VALID_TOTAL_ELEMENTS, VALID_TOTAL_PAGES);

        when(brandServicePort.getBrandsByName(VALID_PAGE,VALID_SIZE, ASC)).thenReturn(brandPageStock);
        when(brandResponseMapper.toBrandResponsePage(brandPageStock,pageable))
                .thenReturn(brandResponsePage);

        Page<BrandResponse> result = brandHandler.getBrandsByName(VALID_PAGE, VALID_SIZE,ASC);

        assertEquals(VALID_TOTAL_ELEMENTS, result.getTotalElements());
        assertEquals(VALID_TOTAL_PAGES, result.getTotalPages());
        assertEquals(VALID_SIZE, result.getSize());
        assertEquals(VALID_BRAND_NAME, result.getContent().get(0).getName());
        assertEquals(VALID_BRAND_DESCRIPTION, result.getContent().get(0).getDescription());
    }
    */
    
}