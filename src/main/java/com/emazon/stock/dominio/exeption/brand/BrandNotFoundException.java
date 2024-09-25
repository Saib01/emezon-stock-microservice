package com.emazon.stock.dominio.exeption.brand;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(ExceptionResponse brandNotFound) {
        super(brandNotFound.getMessage());
    }
}
