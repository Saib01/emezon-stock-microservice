package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryDescriptionTooLongException extends RuntimeException {
    public CategoryDescriptionTooLongException(ExceptionResponse categoryDescriptionTooLong) {
        super(categoryDescriptionTooLong.getMessage());
    }
}
