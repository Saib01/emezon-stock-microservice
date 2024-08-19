package com.emazon.stock.infraestructura.exceptions;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException(String message){
        super(message);
    }
}
