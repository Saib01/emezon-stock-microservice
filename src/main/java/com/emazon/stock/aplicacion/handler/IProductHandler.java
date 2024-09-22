package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.ProductRequest;
import com.emazon.stock.aplicacion.dtos.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductHandler {
        void saveProduct(ProductRequest productRequest);
        Page<ProductResponse> getProductsBySearchTerm(int page, int size, String sortBy, String sortDirection);
        ProductResponse getProduct(Long id);
        void addSupply(Long id,Integer supply);
        boolean validateMaxProductPerCategory(List<Long> listIdsProducts);
}


