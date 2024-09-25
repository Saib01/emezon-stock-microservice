package com.emazon.stock.dominio.exeption.product;

import com.emazon.stock.dominio.exeption.ExceptionResponse;

public class InvalidSupplyException extends RuntimeException {
    public InvalidSupplyException(ExceptionResponse supplyIsInvalid) {
        super(supplyIsInvalid.getMessage());
    }
}