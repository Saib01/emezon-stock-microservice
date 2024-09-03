package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.ProductRequest;
import com.emazon.stock.aplicacion.dtos.ProductResponse;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import org.springframework.data.domain.Page;

public interface IProductHandler {
        void saveProduct(ProductRequest productRequest);
        Page<ProductResponse> getProductsBySearchTerm(int page, int size, String sortBy, String sortDirection);
        ProductResponse getProduct(Long id);
}


