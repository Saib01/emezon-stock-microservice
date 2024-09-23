package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductPageSortByIsInvalidException extends RuntimeException {
    public ProductPageSortByIsInvalidException(ExceptionResponse productPageSortByIsInvalid) {
        super(productPageSortByIsInvalid.getMessage());
    }
}
