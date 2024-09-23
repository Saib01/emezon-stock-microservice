package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductPriceInvalidException extends RuntimeException {
    public ProductPriceInvalidException (ExceptionResponse productPriceGreaterThanZero) {

        super(productPriceGreaterThanZero.getMessage());
    }
}