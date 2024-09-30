package com.emazon.stock.dominio.exeption;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(ExceptionResponse message) {
        super(message.getMessage());
    }
}
