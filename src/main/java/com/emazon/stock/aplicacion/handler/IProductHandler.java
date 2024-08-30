package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.ProductRequest;
import com.emazon.stock.aplicacion.dtos.ProductResponse;

public interface IProductHandler {
        void saveProduct(ProductRequest productRequest);
        ProductResponse getProduct(Long id);
}


