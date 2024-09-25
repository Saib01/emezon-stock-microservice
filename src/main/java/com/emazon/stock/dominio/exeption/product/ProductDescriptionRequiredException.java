package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductDescriptionRequiredException extends RuntimeException {
    public ProductDescriptionRequiredException(ExceptionResponse productDescriptionRequired) {
        super(productDescriptionRequired.getMessage());
    }
}
