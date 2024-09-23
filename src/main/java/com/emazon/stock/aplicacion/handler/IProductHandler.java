package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.request.ProductRequest;
import com.emazon.stock.aplicacion.dtos.response.ProductResponse;
import com.emazon.stock.dominio.modelo.PageStock;

import java.util.List;

public interface IProductHandler {
        void saveProduct(ProductRequest productRequest);
        PageStock<ProductResponse> getProductsBySearchTerm(int page, int size, String sortBy, String sortDirection);
        ProductResponse getProduct(Long id);
        void addSupply(Long id,Integer supply);
        boolean validateMaxProductPerCategory(List<Long> listIdsProducts);
}


