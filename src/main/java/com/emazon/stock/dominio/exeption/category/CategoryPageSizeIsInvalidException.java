package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryPageSizeIsInvalidException extends RuntimeException {
    public CategoryPageSizeIsInvalidException (ExceptionResponse categoryPageSizeNumberIsInvalid) {
        super(categoryPageSizeNumberIsInvalid.getMessage());
    }
}