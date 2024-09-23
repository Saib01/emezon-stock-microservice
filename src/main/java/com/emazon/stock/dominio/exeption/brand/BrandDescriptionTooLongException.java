package com.emazon.stock.dominio.exeption.brand;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class BrandDescriptionTooLongException extends RuntimeException {
    public BrandDescriptionTooLongException(ExceptionResponse brandDescriptionTooLong) {
        super(brandDescriptionTooLong.getMessage());
    }
}
