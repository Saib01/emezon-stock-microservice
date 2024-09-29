package com.emazon.stock.infraestructura.output.jpa.adapter;

import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.BrandEntity;
import com.emazon.stock.infraestructura.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.IBrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.utils.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

        ArgumentCaptor<BrandEntity> brandEntityCaptor= ArgumentCaptor.forClass(BrandEntity.class);
        verify(brandEntityMapper, times(ONE)).toBrandEntity(brand);
        verify(brandRepository, times(ONE)).save(brandEntityCaptor.capture());
        assertEquals(brandEntityCaptor.getValue(),brandEntity);
    }
    
    @Test
    @DisplayName("Should retrieve a brand by its ID")
    void getBrand() {

        when(brandRepository.findById(VALID_ID)).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);
        when(brandRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        Brand result = brandJpaAdapter.getBrand(VALID_ID);
        assertThat(result).isEqualTo(brand);
        assertNull(brandJpaAdapter.getBrand(INVALID_ID));

        verify(brandRepository, times(ONE)).findById(VALID_ID);
        verify(brandEntityMapper, times(ONE)).toBrand(brandEntity);
        verify(brandRepository, times(ONE)).findById(INVALID_ID);
    }


    @Test
    @DisplayName("Should retrieve brand by name")
    void findByName() {
        when(brandRepository.existsByName(VALID_BRAND_NAME)).thenReturn(true);
        brandJpaAdapter.existsByName(VALID_BRAND_NAME);

        verify((brandRepository), times(ONE)).existsByName(VALID_BRAND_NAME);
    }
    @Test
    @DisplayName("Should retrieve brands by name with pagination and sorting")
    void getBrandsByName() {
        Pageable pageable = PageRequest.of(VALID_PAGE, VALID_SIZE,
                Sort.by(Sort.Direction.fromString(ASC), PROPERTY_NAME.toLowerCase()));
        Page<BrandEntity> brandEntityPage = new PageImpl<>(List.of(brandEntity), pageable, VALID_TOTAL_ELEMENTS);
        PageStock<Brand> expectedBrandPageStock = new PageStock<>(List.of(brand),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);

        when(brandRepository.findAll(pageable)).thenReturn(brandEntityPage);
        when(brandEntityMapper.toBrandPageStock(brandEntityPage)).thenReturn(expectedBrandPageStock);

        PageStock<Brand> actualBrandPageStock = brandJpaAdapter.getBrandsByName(VALID_PAGE, VALID_SIZE, ASC);

        assertEquals(expectedBrandPageStock,actualBrandPageStock);
        verify(brandRepository, times(ONE)).findAll(pageable);
        verify(brandEntityMapper, times(ONE)).toBrandPageStock(brandEntityPage);
    }

    @Test
    @DisplayName("Should return true when brand exists by id")
    void shouldReturnTrueWhenBrandExistsById() {
        when(brandRepository.existsById(VALID_ID)).thenReturn(true);

        boolean result = brandJpaAdapter.existsById(VALID_ID);
        assertTrue(result);
    }
}