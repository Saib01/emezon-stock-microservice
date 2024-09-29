package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.api.IProductServicePort;
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

        if(listIdsProducts==null||listIdsProducts.isEmpty()){
            throw new ProductListSizeException(PRODUCT_LIST_SIZE);
        }
        if (listIdsProducts.stream().anyMatch(id -> id <= ZERO)) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        PageStock<Product> productPageStock=this.productPersistencePort.getPaginatedProductsInShoppingCart(
                listIdsProducts,
                validateAndGetFilter(categoryNameFilter, brandNameFilter),
                page, size,sortDirection
        );
        productPageStock.setAscending(ASC.equalsIgnoreCase(sortDirection));
        return productPageStock;
    }

    private List<String> validateAndGetFilter(String categoryNameFilter, String brandNameFilter) {
        List<String> filter = new ArrayList<>();
        filter.add(validateFilterName(categoryNameFilter,this.categoryPersistencePort,CATEGORY));
        filter.add(validateFilterName(brandNameFilter,this.brandPersistencePort,BRAND));

        if (filter.stream().allMatch(Objects::isNull)) {
            throwProductFilterException(BRAND_NAME_OR_CATEGORY);
        }
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

