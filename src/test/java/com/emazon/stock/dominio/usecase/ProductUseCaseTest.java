package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.exeption.brand.BrandNotFoundException;
import com.emazon.stock.dominio.exeption.category.*;
import com.emazon.stock.dominio.exeption.product.*;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.spi.IModelPersistencePort;
import com.emazon.stock.dominio.spi.IProductPersistencePort;
import com.emazon.stock.dominio.utils.PageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        private Product product;
        private Category category;
        private Brand brand;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                category=new Category(VALID_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
                brand = new Brand(VALID_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
                product = new Product(VALID_ID, VALID_PRODUCT_NAME, VALID_PRODUCT_DESCRIPTION, VALID_AMOUNT,
                                VALID_PRICE,
                        brand,
                        new ArrayList<>(Collections.singletonList(category)));

        }

        private void prepareMocksForSaveProduct(boolean categoryExists) {
                prepareMocksForSaveProduct(false,true);
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
                prepareMocksForSaveProduct( true);
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
                                product.setName(null);
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
                                product.setDescription(null);
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
                prepareMocksForSaveProduct(false);

                product.getCategoryList().get(ZERO).setId(INVALID_ID);

                assertThrows(CategoryNotFoundException.class,
                                () -> productUseCase.saveProduct(product));
        }

        @DisplayName("Should not save product when CategoryId is duplicate")
        @Test
        void shouldNotSaveProductWhenCategoryIdIsDuplicate() {
                prepareMocksForSaveProduct(true);

                product.getCategoryList().add(category);

                assertThrows(CategoryDuplicateException.class,
                                () -> productUseCase.saveProduct(product));
        }

        // CategoryListSizeException
        @DisplayName("Should not save product when CategoryListSize is invalid")
        @Test
        void shouldNotSaveProductWhenCategoryListSizeIsInvalid() {
                prepareMocksForSaveProduct(true);

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
                PageStock<Product> expectedProductPageStock = new PageStock<>(List.of(product),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);

                when(productPersistencePort.getProductsBySearchTerm(VALID_PAGE, VALID_SIZE, PageValidator.sortBy(CATEGORY.concat(PROPERTY_NAME)), ASC))
                        .thenReturn(expectedProductPageStock);

                PageStock<Product> actualProductPageStock = productUseCase.getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,CATEGORY_PROPERTY_NAME, ASC);

                assertEquals(expectedProductPageStock, actualProductPageStock);

                verify(productPersistencePort, times(ONE)).getProductsBySearchTerm(VALID_PAGE, VALID_SIZE,PageValidator.sortBy(CATEGORY.concat(PROPERTY_NAME)), ASC);
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


        @Test
        @DisplayName("Should throw InvalidSupplyException when supply is less than or equal to zero")
        void shouldThrowExceptionWhenSupplyIsInvalid() {
                assertThrows(InvalidSupplyException.class, () -> productUseCase.addSupply(VALID_ID,ZERO));
        }

        @Test
        @DisplayName("Should update product amount and save when valid supply is provided")
        void shouldUpdateProductAmountWhenSupplyIsValid() {
                when(productPersistencePort.getProduct(VALID_ID))
                        .thenReturn(product);
                product.setAmount(ZERO);

                productUseCase.addSupply(VALID_ID,VALID_AMOUNT);

                ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
                verify(productPersistencePort).saveProduct(productCaptor.capture());
                assertEquals(VALID_AMOUNT, productCaptor.getValue().getAmount());
        }
        @Test
        @DisplayName("Should update product amount and save when valid supply is provided")
        void shouldNotUpdateProductAmountWhenSupplyIsInvalid() {
                assertThrows(InvalidSupplyException.class,
                        () -> productUseCase.addSupply(VALID_ID,ZERO));
        }

        @Test
        @DisplayName("Should return true when product count per category is within limit")
        void shouldReturnTrueWhenProductCountIsWithinLimit() {
                when(productPersistencePort.getCategoryIdsByProductIds(VALID_LIST_PRODUCTS_IDS)).thenReturn(
                        VALID_LIST_PRODUCTS_IDS.stream().map(
                                id -> List.of(id)
                        ).toList()
                );
                boolean result =productUseCase.validateMaxProductPerCategory(VALID_LIST_PRODUCTS_IDS);
                assertTrue(result);
        }
        @Test
        @DisplayName("Should return false when product count per category exceeds limit")
        void shouldReturnFalseWhenProductCountExceedsLimit() {
                List<Long> combinedList = new ArrayList<>(VALID_LIST_PRODUCTS_IDS);
                combinedList.addAll(VALID_LIST_PRODUCTS_IDS_TWO);
                when(productPersistencePort.getCategoryIdsByProductIds(combinedList)).thenReturn(
                        combinedList.stream().map(
                                id -> List.of(VALID_ID)
                        ).toList()
                );
                boolean result =productUseCase.validateMaxProductPerCategory(combinedList);
                assertFalse(result);
        }

        @Test
        @DisplayName("Should return paginated products when valid parameters are provided. Filter by categoryName and brandName")
        void shouldReturnPaginatedProductsWithFilterByCategoryNameAndBrandName() {
                PageStock<Product> mockProductPageStock = prepareGetPaginatedProductInShoppingCart(VALID_LIST_PRODUCTS_IDS,true);
                assertPageStockProduct(mockProductPageStock,VALID_CATEGORY_NAME,VALID_BRAND_NAME,VALID_LIST_PRODUCTS_IDS);
        }
        @Test
        @DisplayName("Should return paginated products when valid parameters are provided. Filter by categoryName")
        void shouldReturnPaginatedProductsWithFilterByCategoryName() {
                PageStock<Product> mockProductPageStock = prepareGetPaginatedProductInShoppingCart(VALID_LIST_PRODUCTS_IDS,true);
                assertPageStockProduct(mockProductPageStock,VALID_CATEGORY_NAME,null,VALID_LIST_PRODUCTS_IDS);
        }
        @Test
        @DisplayName("Should return paginated products when valid parameters are provided. Filter by brandName")
        void shouldReturnPaginatedProductsWithFilterByBrandName() {
                PageStock<Product> mockProductPageStock = prepareGetPaginatedProductInShoppingCart(VALID_LIST_PRODUCTS_IDS,true);
                assertPageStockProduct(mockProductPageStock,null,VALID_BRAND_NAME,VALID_LIST_PRODUCTS_IDS);
        }
        @Test
        @DisplayName("Should return paginated products when valid parameters are provided. Filter by brandName")
        void shouldReturnPaginatedProductsWhenFilterByBrandNameIsInvalid() {
                PageStock<Product> mockProductPageStock = prepareGetPaginatedProductInShoppingCart(VALID_LIST_PRODUCTS_IDS,false);
                assertThrows(ProductFilterNotFoundException.class,
                        () -> assertPageStockProduct(mockProductPageStock,null,VALID_BRAND_NAME,VALID_LIST_PRODUCTS_IDS)
                );
        }
        @Test
        @DisplayName("Should not return paginated products when products Ids is invalid.")
        void shouldNotReturnPaginatedProductsWhenProductsIdsIsInvalid() {
                PageStock<Product> mockProductPageStock = prepareGetPaginatedProductInShoppingCart(INVALID_LIST_PRODUCTS_IDS,true);
                assertThrows(ProductNotFoundException.class,
                        () -> assertPageStockProduct(mockProductPageStock,null,null,INVALID_LIST_PRODUCTS_IDS)
                );
        }
        @Test
        @DisplayName("Should not return paginated products when products Ids is empty.")
        void shouldNotReturnPaginatedProductsWhenProductsIdsIsEmpty() {
                PageStock<Product> mockProductPageStock = prepareGetPaginatedProductInShoppingCart(EMPTY_LIST,true);
                assertThrows(ProductListSizeException.class,
                        () -> assertPageStockProduct(mockProductPageStock,null,null,EMPTY_LIST)
                );
        }
        @Test
        @DisplayName("Should return false when the product name exist")
        void shouldReturnFalseWhenProductNameExist() {
                String productName="keyboard";
                when(this.productPersistencePort.existsByName(productName)).thenReturn(true);

                boolean result=this.productUseCase.isProductNameAvailable(productName);
                assertEquals(false,result);
        }
        @Test
        @DisplayName("Should reduce the stock after purchasing")
        void shouldReduceTheStockAfterPurchasing() {
                Product productShoppingCart=new Product(VALID_ID, null, null, VALID_AMOUNT,
                        null,
                        null,
                        null);
                when(productPersistencePort.getProductsByProductIds(List.of(VALID_ID))).thenReturn(List.of(product));
                doNothing().when(productPersistencePort).saveAllProduct(anyList());

                List<Product> result=this.productUseCase.reduceStockAfterPurchase(List.of(productShoppingCart));

                assertEquals(productShoppingCart.getAmount(),result.get(ZERO).getAmount());
                verify(productPersistencePort, times(1)).saveAllProduct(anyList());
        }

        @Test
        @DisplayName("Should reduce the stock after purchasing")
        void shouldRestoreStockToPreviousState() {
                Product productShoppingCart=new Product(VALID_ID, null, null, VALID_AMOUNT,
                        null,
                        null,
                        null);
                when(productPersistencePort.getProductsByProductIds(List.of(VALID_ID))).thenReturn(List.of(product));
                doNothing().when(productPersistencePort).saveAllProduct(anyList());

                this.productUseCase.restoreStockToPreviousState(List.of(productShoppingCart));
                verify(productPersistencePort, times(1)).saveAllProduct(anyList());
        }

        private PageStock<Product> prepareGetPaginatedProductInShoppingCart(List<Long> idsProducts,boolean exist) {
                PageStock<Product> mockProductPageStock = new PageStock<>(idsProducts.stream()
                        .map(id ->
                                new Product(
                                        VALID_ID, VALID_PRODUCT_NAME,
                                        VALID_PRODUCT_DESCRIPTION,VALID_AMOUNT, VALID_PRICE,brand,
                                        new ArrayList<>(Collections.singletonList(category))
                                )
                        )
                        .toList(),VALID_TOTAL_ELEMENTS,VALID_TOTAL_PAGES,VALID_PAGE,true,true,VALID_SIZE);
                mockProductPageStock.setAscending(true);
                when(productPersistencePort.getPaginatedProductsInShoppingCart(
                        any(), any(), anyInt(), anyInt(), anyString())).thenReturn(mockProductPageStock);
                when(categoryPersistencePort.existsByName(anyString())).thenReturn(true);
                when(brandPersistencePort.existsByName(anyString())).thenReturn(exist);
                return mockProductPageStock;
        }

        private void assertPageStockProduct(PageStock<Product> mockProductPageStock,String categoryName,String  brandName,List<Long> idsProduct) {
                PageStock<Product> result = productUseCase.getPaginatedProductsInShoppingCart(
                        idsProduct, categoryName, brandName, VALID_PAGE, VALID_SIZE, ASC);

                assertEquals(result, mockProductPageStock);
        }
}
