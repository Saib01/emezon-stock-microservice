package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.api.IProductServicePort;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.spi.IProductPersistencePort;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.utils.Validator;

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
        Validator.validateProduct(product,brandPersistencePort,categoryPersistencePort);
        this.productPersistencePort.saveProduct(product);
    }

    @Override
    public Product getProduct(Long id) {
        return this.productPersistencePort.getProduct(id);
    }
}
