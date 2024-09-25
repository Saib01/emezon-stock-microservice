package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryDescriptionRequiredException extends RuntimeException {
    public CategoryDescriptionRequiredException(ExceptionResponse categoryDescriptionRequired) {
        super(categoryDescriptionRequired.getMessage());
    }
}