package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductPageSizeIsInvalidException extends RuntimeException{
    public ProductPageSizeIsInvalidException(ExceptionResponse productPageSizeNumberIsInvalid) {
        super(productPageSizeNumberIsInvalid.getMessage());
    }
}
