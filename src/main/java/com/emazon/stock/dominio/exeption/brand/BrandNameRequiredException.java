package com.emazon.stock.dominio.exeption.brand;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class BrandNameRequiredException extends RuntimeException {
    public BrandNameRequiredException(ExceptionResponse brandNameRequired) {
        super(brandNameRequired.getMessage());
    }
}