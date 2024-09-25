package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(ExceptionResponse productNotFound) {
        super(productNotFound.getMessage());
    }
}
