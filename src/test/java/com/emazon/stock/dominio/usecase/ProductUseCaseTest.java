package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.exeption.brand.BrandNotFoundException;
import com.emazon.stock.dominio.exeption.category.*;
import com.emazon.stock.dominio.exeption.product.*;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.spi.IModelPersistencePort;
import com.emazon.stock.dominio.spi.IProductPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.dominio.utils.DomainConstants.*;
import static com.emazon.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProductUseCaseTest {
        @Mock
        private IProductPersistencePort productPersistencePort;
        @Mock
        private IBrandPersistencePort brandPersistencePort;
        @Mock
        private ICategoryPersistencePort categoryPersistencePort;
        @InjectMocks
        private ProductUseCase productUseCase;
        Product product;
        Category category;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                category=new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
                product = new Product(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                                VALID_PRICE,
                                new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION),
                                new ArrayList<>(Arrays.asList(category)));

        }

        private void prepareMocksForSaveProduct(boolean productExists, boolean brandExists, boolean categoryExists) {
                prepareMocksForSaveProduct(productExists,brandExists);
                persistencePortExistsById(categoryPersistencePort,categoryExists);
        }
        private void prepareMocksForSaveProduct(boolean productExists, boolean brandExists) {
                when(productPersistencePort.existsByName(product.getName())).thenReturn(productExists);
                persistencePortExistsById(brandPersistencePort,brandExists);
        }
        private void persistencePortExistsById(IModelPersistencePort modelPersistencePort,boolean exists){
                when(modelPersistencePort.existsById(anyLong())).thenReturn(exists);
        }
        @Test
        @DisplayName("Should save the product and verify that the persistence port method is called once")
        void saveProduct() {
                prepareMocksForSaveProduct(false, true, true);
                productUseCase.saveProduct(product);

                ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

                verify(categoryPersistencePort, times(1)).existsById(anyLong());
                verify(brandPersistencePort, times(1)).existsById(anyLong());
                verify(productPersistencePort, times(1)).saveProduct(productCaptor.capture());
                assertEquals(productCaptor.getValue(), product);
        }

        @Test
        @DisplayName("Should not save the product when the product already exists")
        void shouldNotSaveProductWhenProductAlreadyExists() {
                when(productPersistencePort.existsByName(product.getName()))
                                .thenReturn(true);

                assertThrows(ProductAlreadyExistException.class,
                                () -> productUseCase.saveProduct(product));

                verify(productPersistencePort, never()).saveProduct(product);
        }

        @Test
        @DisplayName("Should not save product when name is empty or null")
        void shouldNotSaveProductWhenNameIsEmptyOrNull() {
                assertAll(
                        () -> {
                                product.setName(EMPTY_PROPERTY);
                                assertThrows(ProductNameRequiredException.class, () -> productUseCase.saveProduct(product));
                        },
                        () -> {
                                product.setName(NULL_PROPERTY);
                                assertThrows(ProductNameRequiredException.class, () -> productUseCase.saveProduct(product));
                        }
                );
        }

        @Test
        @DisplayName("Should not save product when description is empty or null")
        void shouldNotSaveProductWhenDescriptionIsEmptyOrNull() {
                assertAll(
                        () -> {
                                product.setDescription(EMPTY_PROPERTY);
                                assertThrows(ProductDescriptionRequiredException.class, () -> productUseCase.saveProduct(product));
                        },
                        () -> {
                                product.setDescription(NULL_PROPERTY);
                                assertThrows(ProductDescriptionRequiredException.class, () -> productUseCase.saveProduct(product));
                        }
                );
        }

        @DisplayName("Should not save product when price is invalid")
        @Test
        void shouldNotSaveProductWhenProductPriceIsInvalid() {
                product.setPrice((double)ZERO);

                assertThrows(ProductPriceInvalidException.class,
                        () -> productUseCase.saveProduct(product));
        }
        @DisplayName("Should not save product when amount is invalid")
        @Test
        void shouldNotSaveProductWhenProductAmountIsInvalid() {
                product.setAmount(ZERO);

                assertThrows(ProductAmountInvalidException.class,
                        () -> productUseCase.saveProduct(product));
        }
        @DisplayName("Should not save product when BrandId is invalid")
        @Test
        void shouldNotSaveProductWhenBrandIdIsInvalid() {
                prepareMocksForSaveProduct(false, false);

                product.getBrand().setId(INVALID_ID);

                assertThrows(BrandNotFoundException.class,
                                () -> productUseCase.saveProduct(product));
        }

        @DisplayName("Should not save product when CategoryId is invalid")
        @Test
        void shouldNotSaveProductWhenCategoryIdIsInvalid() {
                prepareMocksForSaveProduct(false, true,false);

                product.getCategoryList().get(ZERO).setId(INVALID_ID);

                assertThrows(CategoryNotFoundException.class,
                                () -> productUseCase.saveProduct(product));
        }

        @DisplayName("Should not save product when CategoryId is duplicate")
        @Test
        void shouldNotSaveProductWhenCategoryIdIsDuplicate() {
                prepareMocksForSaveProduct(false, true,true);

                product.getCategoryList().add(category);

                assertThrows(CategoryDuplicateException.class,
                                () -> productUseCase.saveProduct(product));
        }

        // CategoryListSizeException
        @DisplayName("Should not save product when CategoryListSize is invalid")
        @Test
        void shouldNotSaveProductWhenCategoryListSizeIsInvalid() {
                prepareMocksForSaveProduct(false, true,true);

                product.getCategoryList().addAll(Collections.nCopies(PRODUCT_MAX_CATEGORY, category));

                assertThrows(CategoryListSizeException.class,
                        () -> productUseCase.saveProduct(product));
        }
        @Test
        @DisplayName("Should return the correct product when fetching a product by ID")
        void shouldGetProduct() {
                when(productPersistencePort.getProduct(VALID_ID))
                                .thenReturn(product);

                Product actualProduct = productUseCase.getProduct(VALID_ID);

                assertNotNull(actualProduct);
                assertEquals(product, actualProduct);

                verify(productPersistencePort, times(1))
                                .getProduct(VALID_ID);
        }
        @Test
        @DisplayName("Should return a paginated page of products")
        void shouldGetProductPageStock() {
                PageStock<Product> expectedProductPageStock = new PageStock<>(
                        Collections.singletonList(product),
                        VALID_TOTAL_ELEMENTS, VALID_TOTAL_PAGES);

                when(productPersistencePort.getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,CATEGORY.toLowerCase(), ASC))
                        .thenReturn(expectedProductPageStock);

                PageStock<Product> actualProductPageStock = productUseCase.getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,CATEGORY_PROPERTY_NAME, ASC);

                assertEquals(expectedProductPageStock, actualProductPageStock);

                verify(productPersistencePort, times(1)).getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,CATEGORY.toLowerCase(), ASC);
        }

        @Test
        @DisplayName("Should not return products when the page number is invalid")
        void shouldNotGetProductPageStockWhenPageNumberIsInvalid() {
                assertThrows(ProductPageNumberIsInvalidException.class,
                        () -> productUseCase.getProductsBySearchTerm(INVALID_PAGE, VALID_SIZE,CATEGORY_PROPERTY_NAME, ASC));
        }

        @Test
        @DisplayName("Should not return products when the page size is invalid")
        void shouldNotGetProductPageStockWhenPageSizeIsInvalid() {
                assertThrows(ProductPageSizeIsInvalidException.class,
                        () -> productUseCase.getProductsBySearchTerm(VALID_PAGE, INVALID_SIZE,CATEGORY_PROPERTY_NAME, ASC));
        }
        @Test
        @DisplayName("Should not return products when the page sorting direction is invalid")
        void shouldNotGetProductPageStockWhenPageSortDirectionIsInvalid() {
                assertThrows(ProductPageSortDirectionIsInvalidException.class,
                        () -> productUseCase.getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,CATEGORY_PROPERTY_NAME, INVALID_SORT_DIRECTION));
        }

        @Test
        @DisplayName("Should not return products when the page sorting by is invalid")
        void shouldNotGetProductPageStockWhenPageSortByIsInvalid() {
                assertThrows(ProductPageSortByIsInvalidException.class,
                        () -> productUseCase.getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,INVALID_CATEGORY_PROPERTY_NAME, ASC));
        }
}
