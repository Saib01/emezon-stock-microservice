package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException(ExceptionResponse categoryAlreadyExists) {
        super(categoryAlreadyExists.getMessage());
    }
}
