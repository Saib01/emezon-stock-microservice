package com.emazon.stock.dominio.spi;

import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.utils.PageStock;

import java.util.List;

public interface IProductPersistencePort extends IModelPersistencePort {
    void saveProduct(Product product);

    PageStock<Product> getProductsBySearchTerm(int page, int size, List<String> sortBy, String sortDirection);

    Product getProduct(Long id);

    List<List<Long>> getCategoryIdsByProductIds(List<Long> listIdsProducts);

    PageStock<Product> getPaginatedProductsInShoppingCart(List<Long> listIdsProducts, List<String> filter, int page, int size, String sortDirection);
}
