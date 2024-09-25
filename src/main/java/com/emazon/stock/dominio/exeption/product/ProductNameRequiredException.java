package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductNameRequiredException extends RuntimeException {
    public ProductNameRequiredException(ExceptionResponse productNameRequired) {
        super(productNameRequired.getMessage());
    }
}
