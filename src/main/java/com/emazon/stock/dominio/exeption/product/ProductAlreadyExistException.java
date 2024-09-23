package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductAlreadyExistException  extends RuntimeException{
    public ProductAlreadyExistException(ExceptionResponse productAlreadyExists) {
        super(productAlreadyExists.getMessage());
    }
}