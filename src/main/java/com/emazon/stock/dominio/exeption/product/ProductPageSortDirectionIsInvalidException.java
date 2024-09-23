package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class ProductPageSortDirectionIsInvalidException extends RuntimeException {
    public ProductPageSortDirectionIsInvalidException(ExceptionResponse productPageSortDirectionIsInvalid) {
        super(productPageSortDirectionIsInvalid.getMessage());
    }
}
