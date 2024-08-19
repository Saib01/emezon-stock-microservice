package com.emazon.stock.infraestructura.exceptions;

public class CategoryNotFoundException extends RuntimeException {
     public CategoryNotFoundException(String message){
        super(message);
    }
}
