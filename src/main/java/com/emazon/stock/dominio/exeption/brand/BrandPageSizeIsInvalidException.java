package com.emazon.stock.dominio.exeption.brand;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class BrandPageSizeIsInvalidException extends RuntimeException{
    public BrandPageSizeIsInvalidException(ExceptionResponse brandPageSizeNumberIsInvalid) {
        super(brandPageSizeNumberIsInvalid.getMessage());
    }
}
