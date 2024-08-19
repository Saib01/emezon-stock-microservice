package com.emazon.stock.infraestructura.exceptions;

public class CategoryNameTooLongException extends RuntimeException {
    public CategoryNameTooLongException(String message){
        super(message);
    }
}
