package com.emazon.stock.dominio.api;

import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.utils.PageStock;

import java.util.List;

public interface IProductServicePort {
    void saveProduct(Product product);

    PageStock<Product> getProductsBySearchTerm(int page, int size, String sortBy, String sortDirection);

    Product getProduct(Long id);

    void addSupply(Long id, Integer supply);

    boolean validateMaxProductPerCategory(List<Long> listIdsProducts);

    PageStock<Product> getPaginatedProductsInShoppingCart(List<Long> listIdsProducts, String categoryName, String brandName, int page, int size, String sortDirection);

    List<Product> reduceStockAfterPurchase(List<Product> productRequestList);

    void restoreStockToPreviousState(List<Product> list);

    Boolean isProductNameAvailable(String productName);
}
