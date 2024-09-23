package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryPageNumberIsInvalidException extends RuntimeException {
    public CategoryPageNumberIsInvalidException(ExceptionResponse categoryPageNumberIsInvalid) {
        super(categoryPageNumberIsInvalid.getMessage());
    }
}