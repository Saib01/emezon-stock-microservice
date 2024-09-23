package com.emazon.stock.dominio.exeption.brand;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class BrandPageSortDirectionIsInvalidException extends RuntimeException {
    public BrandPageSortDirectionIsInvalidException(ExceptionResponse brandPageSortDirectionIsInvalid) {
        super(brandPageSortDirectionIsInvalid.getMessage());
    }
}
