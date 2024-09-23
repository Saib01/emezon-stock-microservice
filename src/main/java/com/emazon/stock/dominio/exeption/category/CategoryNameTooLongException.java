package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryNameTooLongException extends RuntimeException {
    public CategoryNameTooLongException(ExceptionResponse categoryNameTooLong) {
        super(categoryNameTooLong.getMessage());
    }
}
