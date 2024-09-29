package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.ShoppingCartListRequest;
import com.emazon.stock.aplicacion.dtos.product.ProductRequest;
import com.emazon.stock.aplicacion.dtos.product.ProductResponse;
import com.emazon.stock.dominio.utils.PageStock;

import java.util.List;

public interface IProductHandler {
    void saveProduct(ProductRequest productRequest);

    PageStock<ProductResponse> getProductsBySearchTerm(int page, int size, String sortBy, String sortDirection);

    ProductResponse getProduct(Long id);

    void addSupply(Long id, Integer supply);

    boolean validateMaxProductPerCategory(List<Long> listIdsProducts);

    PageStock<ProductResponse> getPaginatedProductsInShoppingCart(int page, int size, String sortDirection, ShoppingCartListRequest shoppingCartListRequest);
}


