package com.emazon.stock.dominio.utils;

public class ErrorTemplates {

    public static final String NOT_FOUND = "No %s was found with that number";
    public static final String ALREADY_EXISTS = "There is already a %s with that name";
    public static final String TOO_LONG = "The %s %s has a maximum allowed characters of %d";
    public static final String REQUIRED = "The %s %s cannot be null or empty.";
    public static final String PAGE_SORT_IS_INVALID = "The sort direction for retrieving the %s page is invalid. Please use 'ASC' for ascending or 'DESC' for descending.";
    public static final String PAGE_NUMBER_IS_INVALID = "The page number for %s must be greater than or equal to zero.";
    public static final String PAGE_SIZE_IS_INVALID = "The page size to retrieve the %s page must be greater than or equal to one.";
    public static final String GREATER_THAN_ZERO="The %s %s must be greater than zero.";
    private ErrorTemplates() {
    }
}
