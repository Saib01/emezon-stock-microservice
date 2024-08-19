package com.emazon.stock.infraestructura.exceptions;

public class CategoryDescriptionRequiredException extends RuntimeException {
    public CategoryDescriptionRequiredException(String message){
        super(message);
    }
}