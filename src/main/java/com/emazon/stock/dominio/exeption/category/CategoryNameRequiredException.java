package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryNameRequiredException extends RuntimeException {
    public CategoryNameRequiredException(ExceptionResponse categoryNameRequired) {
        super(categoryNameRequired.getMessage());
    }
}