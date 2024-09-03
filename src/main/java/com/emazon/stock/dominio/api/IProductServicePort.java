package com.emazon.stock.dominio.api;

import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.modelo.Product;

public interface IProductServicePort {
    void saveProduct(Product product);
    PageStock<Product> getProductsBySearchTerm(int page, int size,String sortBy, String sortDirection);
    Product getProduct(Long id);
}
