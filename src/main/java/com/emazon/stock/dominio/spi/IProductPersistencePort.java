package com.emazon.stock.dominio.spi;

import com.emazon.stock.dominio.modelo.Product;

public interface IProductPersistencePort extends IModelPersistencePort {
    void saveProduct(Product product);
    Product getProduct(Long id);
}
