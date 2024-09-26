package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductListSizeException extends RuntimeException {
    public ProductListSizeException(ExceptionResponse productListSize) {
        super(productListSize.getMessage());
    }
}