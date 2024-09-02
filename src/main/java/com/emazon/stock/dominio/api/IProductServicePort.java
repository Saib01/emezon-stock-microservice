package com.emazon.stock.dominio.api;

import com.emazon.stock.dominio.modelo.Product;

public interface IProductServicePort {
    void saveProduct(Product product);
    Product getProduct(Long id);
}
