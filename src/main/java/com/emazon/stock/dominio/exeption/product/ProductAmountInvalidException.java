package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductAmountInvalidException extends RuntimeException{
    public ProductAmountInvalidException(ExceptionResponse productAmountGreaterThanZero) {
        super(productAmountGreaterThanZero.getMessage());
    }
}