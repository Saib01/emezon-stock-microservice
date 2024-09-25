package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryListSizeException extends RuntimeException {
    public CategoryListSizeException(ExceptionResponse categoryListSize) {
        super(categoryListSize.getMessage());
    }
}