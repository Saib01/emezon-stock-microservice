package com.emazon.stock.dominio.exeption.brand;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class BrandDescriptionRequiredException extends RuntimeException {
    public BrandDescriptionRequiredException(ExceptionResponse brandDescriptionRequired) {
        super(brandDescriptionRequired.getMessage());
    }
}