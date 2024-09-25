package com.emazon.stock.dominio.exeption.brand;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class BrandPageNumberIsInvalidException extends RuntimeException {
    public BrandPageNumberIsInvalidException(ExceptionResponse brandPageNumberIsInvalid) {
        super(brandPageNumberIsInvalid.getMessage());
    }
}
