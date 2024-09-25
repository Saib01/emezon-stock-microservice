package com.emazon.stock.dominio.exeption.brand;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class BrandNameTooLongException extends RuntimeException {
    public BrandNameTooLongException(ExceptionResponse brandNameTooLong) {
        super(brandNameTooLong.getMessage());
    }
}
