package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(ExceptionResponse categoryNotFound) {
        super(categoryNotFound.getMessage());
    }
}
