package com.emazon.stock.dominio.exeption.brand;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class BrandAlreadyExistException extends RuntimeException {
    public BrandAlreadyExistException(ExceptionResponse brandAlreadyExists) {
        super(brandAlreadyExists.getMessage());
    }
}
