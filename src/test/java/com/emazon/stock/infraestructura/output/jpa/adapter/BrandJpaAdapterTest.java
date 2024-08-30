package com.emazon.stock.infraestructura.output.jpa.adapter;

import com.emazon.stock.dominio.exeption.brand.BrandNotFoundException;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.infraestructura.output.jpa.entity.BrandEntity;
import com.emazon.stock.infraestructura.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.IBrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static com.emazon.stock.constants.TestConstants.VALID_ID;
import static com.emazon.stock.constants.TestConstants.INVALID_ID;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_NAME;
import static com.emazon.stock.constants.TestConstants.VALID_BRAND_DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class BrandJpaAdapterTest {

    @Mock
    private BrandEntityMapper brandEntityMapper ;
    @InjectMocks
    private  BrandJpaAdapter brandJpaAdapter;
    @Mock
    private IBrandRepository brandRepository;
    BrandEntity brandEntity;
    Brand brand;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brandEntity=new BrandEntity(VALID_ID,VALID_BRAND_NAME,VALID_BRAND_DESCRIPTION);
        brand=new Brand(VALID_ID,VALID_BRAND_NAME,VALID_BRAND_DESCRIPTION);
    }
    @Test
    @DisplayName("Should save the brand and verify that the brand repository method is called once")
    void saveBrand() {

        when(brandEntityMapper.toBrandEntity(brand)).thenReturn(brandEntity);
        brandJpaAdapter.saveBrand(brand);

        verify(brandEntityMapper, times(1)).toBrandEntity(brand);
        verify(brandRepository, times(1)).save(brandEntity);
    }
    
    @Test
    @DisplayName("Should retrieve a brand by its ID")
    void getBrand() {

        when(brandRepository.findById(VALID_ID)).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandJpaAdapter.getBrand(VALID_ID);
        assertThat(result).isEqualTo(brand);

        verify(brandRepository, times(1)).findById(VALID_ID);
        verify(brandEntityMapper, times(1)).toBrand(brandEntity);
    }

    @Test
    @DisplayName("Should throw BrandNotFoundException when brand is not found")
    void getBrandWhenNotFoundShouldThrowException() {
        when(brandRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
        assertThrows(BrandNotFoundException.class, () -> brandJpaAdapter.getBrand(INVALID_ID));
    }

    @Test
    @DisplayName("Should retrieve brand by name")
    void findByName() {
        when(brandRepository.existsByName(VALID_BRAND_NAME)).thenReturn(true);
        brandJpaAdapter.findByName(VALID_BRAND_NAME);

        verify((brandRepository), times(1)).existsByName(VALID_BRAND_NAME);
    }
}