package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.api.IProductServicePort;
import com.emazon.stock.dominio.exeption.InsufficientStockException;
import com.emazon.stock.dominio.exeption.product.InvalidSupplyException;
import com.emazon.stock.dominio.exeption.product.ProductFilterNotFoundException;
import com.emazon.stock.dominio.exeption.product.ProductListSizeException;
import com.emazon.stock.dominio.exeption.product.ProductNotFoundException;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.spi.IModelPersistencePort;
import com.emazon.stock.dominio.spi.IProductPersistencePort;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.dominio.utils.PageValidator;
import com.emazon.stock.dominio.utils.Validator;

import java.util.*;
import java.util.stream.Collectors;

import static com.emazon.stock.dominio.exeption.ExceptionResponse.*;
import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.dominio.utils.DomainConstants.*;
import static com.emazon.stock.dominio.utils.Validator.validateIsGreaterThanZero;
import static java.lang.String.format;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort,
                          IBrandPersistencePort brandPersistencePort,
                          ICategoryPersistencePort categoryPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveProduct(Product product) {
        Validator.validateNameIsAlreadyInUse(this.productPersistencePort, product);
        Validator.validateProduct(product, brandPersistencePort, categoryPersistencePort);
        this.productPersistencePort.saveProduct(product);
    }

    @Override
    public PageStock<Product> getProductsBySearchTerm(int page, int size, String sortBy, String sortDirection) {
        PageValidator.parameters(page, size, sortDirection, Product.class.getSimpleName());
        PageStock<Product> productPageStock = this.productPersistencePort.getProductsBySearchTerm(page, size,
                PageValidator.sortBy(sortBy), sortDirection);
        productPageStock.setAscending(ASC.equalsIgnoreCase(sortDirection));
        return productPageStock;
    }


    @Override
    public Product getProduct(Long id) {
        return Optional.ofNullable(productPersistencePort.getProduct(id))
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

    }

    @Override
    public void addSupply(Long id, Integer supply) {
        if (supply <= ZERO) {
            throw new InvalidSupplyException(SUPPLY_IS_INVALID);
        }
        Product product = this.getProduct(id);
        product.setAmount(product.getAmount() + supply);
        this.productPersistencePort.saveProduct(product);
    }

    @Override
    public boolean validateMaxProductPerCategory(List<Long> listIdsProducts) {
        List<List<Long>> categoryIdsListByProducts = this.productPersistencePort.getCategoryIdsByProductIds(listIdsProducts);

        return categoryIdsListByProducts.stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(categoryId -> categoryId, Collectors.counting()))
                .values().stream()
                .noneMatch(count -> count > MAX_ARTICLES_PER_CATEGORY);
    }

    @Override
    public PageStock<Product> getPaginatedProductsInShoppingCart(List<Long> listIdsProducts, String categoryNameFilter, String brandNameFilter, int page, int size, String sortDirection) {
        PageValidator.parameters(page, size, sortDirection, Product.class.getSimpleName());

        validateProductIdList(listIdsProducts);

        PageStock<Product> productPageStock=this.productPersistencePort.getPaginatedProductsInShoppingCart(
                listIdsProducts,
                validateAndGetFilter(categoryNameFilter, brandNameFilter),
                page, size,sortDirection
        );
        productPageStock.setAscending(ASC.equalsIgnoreCase(sortDirection));
        return productPageStock;
    }

    @Override
    public List<Product> reduceStockAfterPurchase(List<Product> productShoppingCart) {
        return adjustProductStockFromShoppingCart(productShoppingCart,false);
    }

    @Override
    public void restoreStockToPreviousState(List<Product> productShoppingCart) {
        adjustProductStockFromShoppingCart(productShoppingCart,true);
    }

    @Override
    public Boolean isProductNameAvailable(String productName) {
        return !productPersistencePort.existsByName(productName);
    }

    private List<Product> adjustProductStockFromShoppingCart(List<Product> productShoppingCart, boolean isRestoring) {
        List<Product> products = validateAndGetProductsFromShoppingCart(productShoppingCart);
        Map<Long, Integer> unitsInCart = productShoppingCart.stream()
                .collect(Collectors.toMap(Product::getId, Product::getAmount));

        adjustAmountFromProductList(unitsInCart, products,isRestoring);

        productPersistencePort.saveAllProduct(products);
        products.forEach(product -> product.setAmount(unitsInCart.get(product.getId())));
        return products;
    }

    private List<Product> validateAndGetProductsFromShoppingCart(List<Product> productShoppingCart) {
        validateList(productShoppingCart);
        List<Long> productIdsList=productShoppingCart.stream().map(
                product -> {
                    validateIsGreaterThanZero(product.getAmount(), PROPERTY_AMOUNT);
                    validateIsGreaterThanZero(product.getId(),PROPERTY_ID);
                    return product.getId();
                }
        ).toList();
        return productPersistencePort.getProductsByProductIds(productIdsList);
    }

    private static void adjustAmountFromProductList(Map<Long, Integer> unitsInCart, List<Product> products, boolean isRestoring) {
        products.forEach(product -> {

            int stock = product.getAmount();
            int units = unitsInCart.get(product.getId());
            if (!isRestoring && stock < units) {
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            product.setAmount(isRestoring ? stock + units : stock - units);
        });
    }


    private static void validateProductIdList(List<Long> listIdsProducts) {
        validateList(listIdsProducts);
        if (listIdsProducts.stream().anyMatch(id -> id <= ZERO)) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
    }
    private static <T> void validateList(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new ProductListSizeException(PRODUCT_LIST_SIZE);
        }
    }

    private List<String> validateAndGetFilter(String categoryNameFilter, String brandNameFilter) {
        List<String> filter = new ArrayList<>();
        filter.add(validateFilterName(categoryNameFilter,this.categoryPersistencePort,CATEGORY));
        filter.add(validateFilterName(brandNameFilter,this.brandPersistencePort,BRAND));
        return filter;
    }

    private String validateFilterName(String name, IModelPersistencePort modelPersistencePort,String modelName){
        if (name==null||name.isEmpty()) {
            return null;
        }
        if (!modelPersistencePort.existsByName(name)) {
            throwProductFilterException(modelName);
        }
        return name;
    }
    private static void throwProductFilterException(String errorName) {
        throw new ProductFilterNotFoundException(format(PRODUCT_FILTER_NOT_FOUND.getMessage(),errorName)
        );
    }
}

