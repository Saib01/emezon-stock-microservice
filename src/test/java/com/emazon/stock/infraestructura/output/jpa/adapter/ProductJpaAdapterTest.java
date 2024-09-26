package com.emazon.stock.infraestructura.output.jpa.adapter;

import com.emazon.stock.dominio.exeption.product.ProductNotFoundException;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.infraestructura.output.jpa.entity.BrandEntity;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import com.emazon.stock.infraestructura.output.jpa.entity.ProductEntity;
import com.emazon.stock.infraestructura.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.dominio.utils.Direction.DESC;
import static com.emazon.stock.dominio.utils.DomainConstants.*;
import static com.emazon.stock.dominio.utils.PageValidator.getSortProperties;
import static com.emazon.stock.infraestructura.output.jpa.adapter.ProductJpaAdapter.ORDER_BY_CATEGORY;
import static com.emazon.stock.utils.TestConstants.*;
import static com.emazon.stock.utils.TestConstants.VALID_CATEGORY_NAME;
import static java.math.BigInteger.TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProductJpaAdapterTest {
    public static final int FOUR = 4;
    @Mock
    private ProductEntityMapper productEntityMapper ;
    @InjectMocks
    private  ProductJpaAdapter productJpaAdapter;
    @Mock
    private IProductRepository productRepository;
    ProductEntity productEntity;
    CategoryEntity categoryEntity;
    Product product;
    Category category;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryEntity=new CategoryEntity(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        category=new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);

        productEntity = createProductEntity(VALID_ID);

        product= new Product(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                VALID_PRICE,
                new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION),
                new ArrayList<>(Arrays.asList(category)));
    }
    @Test
    @DisplayName("Should save the product and verify that the product repository method is called once")
    void saveProduct() {

        when(productEntityMapper.toProductEntity(product)).thenReturn(productEntity);
        productJpaAdapter.saveProduct(product);
        ArgumentCaptor<ProductEntity> productEntityCaptor= ArgumentCaptor.forClass(ProductEntity.class);

        verify(productEntityMapper, times(ONE)).toProductEntity(product);
        verify(productRepository, times(ONE)).save(productEntityCaptor.capture());
        assertEquals(productEntityCaptor.getValue(), productEntity);
    }
    @Test
    @DisplayName("Should retrieve a product by its ID")
    void getProduct() {

        when(productRepository.findById(VALID_ID)).thenReturn(Optional.of(productEntity));
        when(productEntityMapper.toProduct(productEntity)).thenReturn(product);
        when(productRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        Product result = productJpaAdapter.getProduct(VALID_ID);
        assertThat(result).isEqualTo(product);
        assertNull(productJpaAdapter.getProduct(INVALID_ID));

        verify(productRepository, times(ONE)).findById(VALID_ID);
        verify(productEntityMapper, times(ONE)).toProduct(productEntity);
        verify(productRepository, times(ONE)).findById(INVALID_ID);

    }


    @Test
    @DisplayName("Should retrieve product by name")
    void findByName() {
        when(productRepository.existsByName(VALID_CATEGORY_NAME)).thenReturn(true);
        productJpaAdapter.existsByName(VALID_CATEGORY_NAME);

        verify((productRepository), times(ONE)).existsByName(VALID_CATEGORY_NAME);
    }

    @Test
    @DisplayName("Should retrieve products by name with pagination and sorting")
    void getProductsByName() {
        Page<ProductEntity> productEntityPage = new PageImpl<>(List.of(productEntity), PageRequest.of(VALID_PAGE, VALID_SIZE), VALID_TOTAL_ELEMENTS);
        PageStock<Product> expectedProductPageStock = new PageStock<>(List.of(product),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);
        when(productEntityMapper.toProductPageStock(productEntityPage)).thenReturn(expectedProductPageStock);

        when(productRepository.findAllOrderByCategoryNameAndProductNameAndBrandNameASC(any())).thenReturn(productEntityPage);
        when(productRepository.findAllOrderByCategoryNameAndProductNameAndBrandNameDESC(any())).thenReturn(productEntityPage);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(productEntityPage);

        assertEqualsAndVerifyPageStock(productEntityPage, expectedProductPageStock,List.of(ORDER_BY_CATEGORY),ASC);
        assertEqualsAndVerifyPageStock(productEntityPage, expectedProductPageStock,List.of(ORDER_BY_CATEGORY),DESC);
        assertEqualsAndVerifyPageStock(productEntityPage, expectedProductPageStock,getSortProperties(ONE,ZERO),ASC);
        assertEqualsAndVerifyPageStock(productEntityPage, expectedProductPageStock,getSortProperties(ZERO,ONE),DESC);

        verify(productRepository, times(ONE)).findAllOrderByCategoryNameAndProductNameAndBrandNameASC(any());
        verify(productRepository, times(ONE)).findAllOrderByCategoryNameAndProductNameAndBrandNameDESC(any());
        verify(productRepository, times(TWO.intValue())).findAll(any(Pageable.class));
        verify(productEntityMapper, times(FOUR)).toProductPageStock(productEntityPage);
    }

    private void assertEqualsAndVerifyPageStock(Page<ProductEntity> productEntityPage, PageStock<Product> expectedProductPageStock,List<String> sortBy,String sortDirection) {
        PageStock<Product> actualProductPageStock = productJpaAdapter.getProductsBySearchTerm(VALID_PAGE,VALID_SIZE,sortBy,sortDirection);
        assertEquals(expectedProductPageStock,actualProductPageStock);
    }

    @Test
    @DisplayName("Should return true when product exists by id")
    void shouldReturnTrueWhenProductExistsById() {
        when(productRepository.existsById(VALID_ID)).thenReturn(true);

        boolean result = productJpaAdapter.existsById(VALID_ID);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return category IDs by product IDs")
    void shouldReturnCategoryIdsByProductIds() {
        List<ProductEntity> productEntityList=Arrays.asList(productEntity, createProductEntity(VALID_ID_TWO));
        when(productRepository.findByIdIn(VALID_LIST_PRODUCTS_IDS)).thenReturn(productEntityList);

        List<List<Long>> result = productJpaAdapter.getCategoryIdsByProductIds(VALID_LIST_PRODUCTS_IDS);

        assertEquals(productEntityList.size(), result.size());
        assertEquals(productEntityList.get(ZERO).getCategoryEntityList().get(ZERO).getId(), result.get(ZERO).get(ZERO));
        assertEquals(productEntityList.get(ONE).getCategoryEntityList().get(ZERO).getId(), result.get(ONE).get(ZERO));
    }

    ProductEntity createProductEntity(Long id){
        return new ProductEntity(id, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,VALID_PRICE,
                createBrandEntity(),
                createCategoryEntityList(id)
        );
    }
    BrandEntity createBrandEntity(){
        return new BrandEntity(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }
    List<CategoryEntity> createCategoryEntityList(Long id){
        return Arrays.asList(new CategoryEntity(id, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION));
    }

}