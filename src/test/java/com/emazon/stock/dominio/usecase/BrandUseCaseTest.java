package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.exeption.brand.BrandNameTooLongException;
import com.emazon.stock.dominio.exeption.brand.BrandNameRequiredException;
import com.emazon.stock.dominio.exeption.brand.BrandDescriptionRequiredException;
import com.emazon.stock.dominio.exeption.brand.BrandDescriptionTooLongException;
import com.emazon.stock.dominio.exeption.brand.BrandAlreadyExistException;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.emazon.stock.constants.TestConstants.VALID_ID;
import static com.emazon.stock.constants.TestConstants.INVALID_BRAND_NAME;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_NAME;
import static com.emazon.stock.constants.TestConstants.NULL_PROPERTY;
import static com.emazon.stock.constants.TestConstants.EMPTY_PROPERTY;
import static com.emazon.stock.constants.TestConstants.INVALID_BRAND_DESCRIPTION;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_DESCRIPTION;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;
    @InjectMocks
    private BrandUseCase brandUseCase;
    Brand brand ;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brand =new Brand(VALID_ID,VALID_BRAND_NAME,VALID_BRAND_DESCRIPTION);
    }

    @Test
    @DisplayName("Should save the brand and verify that the persistence port method is called once")
    void saveBrand() {
        when(this.brandPersistencePort.findByName(brand.getName()))
                .thenReturn(false);

        brandUseCase.saveBrand(brand);

        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }

    @Test
    @DisplayName("Should not save the brand when the brand already exists")
    void shouldNotSaveBrandWhenBrandAlreadyExists() {
        when(brandPersistencePort.findByName(brand.getName()))
                .thenReturn(true);

        assertThrows(BrandAlreadyExistException.class,
                () ->brandUseCase.saveBrand(brand)
        );

        verify(brandPersistencePort, never()).saveBrand(brand);
    }
    @Test
    @DisplayName("Should not save brand when name is empty")
    void shouldNotSaveBrandWhenNameIsEmpty() {
        brand.setName(EMPTY_PROPERTY);
        assertThrows(BrandNameRequiredException.class,
                () -> brandUseCase.saveBrand(brand)
        );
    }

    @Test
    @DisplayName("Should not save brand when name is null")
    void shouldNotSaveBrandWhenNameIsNull() {
        brand.setName(NULL_PROPERTY);
        assertThrows(BrandNameRequiredException.class,
                () -> brandUseCase.saveBrand(brand)
        );
    }

    @Test
    @DisplayName("Should not save brand when description is null")
    void shouldNotSaveBrandWhenDescriptionIsNull() {
        brand.setDescription(NULL_PROPERTY);
        assertThrows(BrandDescriptionRequiredException.class,
                () -> brandUseCase.saveBrand(brand)
        );
    }

    @DisplayName("Should not save brand when description is empty")
    @Test
    void shouldNotSaveBrandWhenDescriptionIsEmpty() {
        brand.setDescription(EMPTY_PROPERTY);
        assertThrows(BrandDescriptionRequiredException.class,
                () -> brandUseCase.saveBrand(brand)
        );
    }

    @Test
    @DisplayName("Should not save brand when description is too long")
    void shouldNotSaveBrandWhenDescriptionIsTooLong() {
        brand.setDescription(INVALID_BRAND_DESCRIPTION);
        assertThrows(BrandDescriptionTooLongException.class,
                () -> brandUseCase.saveBrand(brand)
        );
    }

    @Test
    @DisplayName("Should not save brand when name is too long")
    void shouldNotSaveBrandWhenNameIsTooLong() {
        brand.setName(INVALID_BRAND_NAME);
        assertThrows(BrandNameTooLongException.class,
                () -> brandUseCase.saveBrand(brand)
        );
    }

    @Test
    @DisplayName("Should return the correct brand when fetching a brand by ID")
    void shouldGetBrand() {
        when(brandPersistencePort.getBrand(VALID_ID))
                .thenReturn(brand);

        Brand actualBrand = brandUseCase.getBrand(VALID_ID);

        assertNotNull(actualBrand);
        assertEquals(brand.getName(), actualBrand.getName());
        assertEquals(brand.getDescription(), actualBrand.getDescription());
        assertEquals(brand.getId(), actualBrand.getId());

        verify(brandPersistencePort, times(1))
                .getBrand(VALID_ID);
    }



}