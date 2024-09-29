package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.exeption.brand.BrandNameTooLongException;
import com.emazon.stock.dominio.exeption.brand.BrandNameRequiredException;
import com.emazon.stock.dominio.exeption.brand.BrandDescriptionRequiredException;
import com.emazon.stock.dominio.exeption.brand.BrandDescriptionTooLongException;
import com.emazon.stock.dominio.exeption.brand.BrandAlreadyExistException;
import com.emazon.stock.dominio.exeption.brand.BrandPageNumberIsInvalidException;
import com.emazon.stock.dominio.exeption.brand.BrandPageSizeIsInvalidException;
import com.emazon.stock.dominio.exeption.brand.BrandPageSortDirectionIsInvalidException;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
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
        when(this.brandPersistencePort.existsByName(brand.getName()))
                .thenReturn(false);

        brandUseCase.saveBrand(brand);

        ArgumentCaptor<Brand> brandCaptor = ArgumentCaptor.forClass(Brand.class);

        verify(brandPersistencePort, times(ONE)).saveBrand(brandCaptor.capture());
        assertEquals(brandCaptor.getValue(), brand);

    }

    @Test
    @DisplayName("Should not save the brand when the brand already exists")
    void shouldNotSaveBrandWhenBrandAlreadyExists() {
        when(brandPersistencePort.existsByName(brand.getName()))
                .thenReturn(true);

        assertThrows(BrandAlreadyExistException.class,
                () ->brandUseCase.saveBrand(brand)
        );

        verify(brandPersistencePort, never()).saveBrand(brand);
    }

    @Test
    @DisplayName("Should not save brand when name is empty or null")
    void shouldNotSaveBrandWhenNameIsEmptyOrNull() {
        assertAll(
                () -> {
                    brand.setName(EMPTY_PROPERTY);
                    assertThrows(BrandNameRequiredException.class, () -> brandUseCase.saveBrand(brand));
                },
                () -> {
                    brand.setName(NULL_PROPERTY);
                    assertThrows(BrandNameRequiredException.class, () -> brandUseCase.saveBrand(brand));
                }
        );
    }

    @Test
    @DisplayName("Should not save brand when description is empty or null")
    void shouldNotSaveBrandWhenDescriptionIsEmptyOrNull() {
        assertAll(
                () -> {
                    brand.setDescription(EMPTY_PROPERTY);
                    assertThrows(BrandDescriptionRequiredException.class, () -> brandUseCase.saveBrand(brand));
                },
                () -> {
                    brand.setDescription(NULL_PROPERTY);
                    assertThrows(BrandDescriptionRequiredException.class, () -> brandUseCase.saveBrand(brand));
                }
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
        assertEquals(brand, actualBrand);

        verify(brandPersistencePort, times(ONE))
                .getBrand(VALID_ID);
    }
    @Test
    @DisplayName("Should return a paginated page of brand")
    void shouldGetBrandPageStock() {
        PageStock<Brand> expectedBrandPageStock = new PageStock<>(
                Collections.singletonList(brand),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE
        );

        when(brandPersistencePort.getBrandsByName(VALID_PAGE, VALID_SIZE,ASC))
                .thenReturn(expectedBrandPageStock);

        PageStock<Brand> actualBrandPageStock=brandUseCase
                .getBrandsByName(VALID_PAGE, VALID_SIZE,ASC);

        assertEquals(expectedBrandPageStock, actualBrandPageStock);

        verify(brandPersistencePort, times(ONE))
                .getBrandsByName(VALID_PAGE, VALID_SIZE,ASC);
    }
    @Test
    @DisplayName("Should not return brand when the page number is invalid")
    void shouldNotGetBrandPageStockWhenPageNumberIsInvalid() {
        assertThrows(BrandPageNumberIsInvalidException.class,
                () -> brandUseCase.getBrandsByName(INVALID_PAGE, VALID_SIZE,ASC)
        );
    }
    @Test
    @DisplayName("Should not return brand when the page size is invalid")
    void shouldNotGetBrandPageStockWhenPageSizeIsInvalid() {
        assertThrows(BrandPageSizeIsInvalidException.class,
                () -> brandUseCase.getBrandsByName(VALID_PAGE, INVALID_SIZE,ASC)
        );
    }
    @Test
    @DisplayName("Should not return brand when the page sorting direction is invalid")
    void shouldNotGetBrandPageStockWhenPageSortDirectionIsInvalid() {
        assertThrows(BrandPageSortDirectionIsInvalidException.class,
                () -> brandUseCase.getBrandsByName(VALID_PAGE, VALID_SIZE,INVALID_SORT_DIRECTION)
        );
    }


}