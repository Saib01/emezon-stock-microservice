package com.emazon.stock.dominio.exeption.category;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class CategoryPageSortDirectionIsInvalidException extends RuntimeException {
    public CategoryPageSortDirectionIsInvalidException(ExceptionResponse categoryPageSortDirectionIsInvalid) {
        super(categoryPageSortDirectionIsInvalid.getMessage());
    }
}
