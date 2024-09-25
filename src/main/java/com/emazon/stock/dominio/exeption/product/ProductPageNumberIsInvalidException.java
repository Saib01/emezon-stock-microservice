package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductPageNumberIsInvalidException extends RuntimeException {
    public ProductPageNumberIsInvalidException(ExceptionResponse productPageNumberIsInvalid) {
        super(productPageNumberIsInvalid.getMessage());
    }
}
