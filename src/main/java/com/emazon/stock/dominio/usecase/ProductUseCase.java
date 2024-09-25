package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.api.IProductServicePort;
import com.emazon.stock.dominio.exeption.product.InvalidSupplyException;
import com.emazon.stock.dominio.exeption.product.ProductNotFoundException;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.spi.IProductPersistencePort;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.dominio.utils.PageValidator;
import com.emazon.stock.dominio.utils.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.emazon.stock.dominio.exeption.ExceptionResponse.PRODUCT_NOT_FOUND;
import static com.emazon.stock.dominio.exeption.ExceptionResponse.SUPPLY_IS_INVALID;
import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.dominio.utils.DomainConstants.MAX_ARTICLES_PER_CATEGORY;
import static com.emazon.stock.dominio.utils.DomainConstants.ZERO;

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
}

