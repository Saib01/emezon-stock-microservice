package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryDuplicateException extends RuntimeException {
    public CategoryDuplicateException(ExceptionResponse categoryDuplicate) {
        super(categoryDuplicate.getMessage());
    }
}