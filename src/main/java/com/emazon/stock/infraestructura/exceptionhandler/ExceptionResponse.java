package com.emazon.stock.infraestructura.exceptionhandler;

public enum ExceptionResponse {
    CATEGORY_NOT_FOUND("No Category was found with that number"),
    CATEGORY_ALREADY_EXISTS("There is already a Category with that name"),
    CATEGORY_DESCRIPTION_TOO_LONG("The description has a maximum allowed characters of 90"),
    CATEGORY_NAME_TOO_LONG("The name has a maximum allowed characters of 50"),
    CATEGORY_DESCRIPTION_REQUIRED("Category description cannot be null or empty."),
    CATEGORY_NAME_REQUIRED("Category name cannot be null or empty."),

    CATEGORY_PAGE_SORT_DIRECTION_IS_INVALID("The sort direction for retrieving categories is invalid. Please use 'ASC' for ascending or 'DESC' for descending."),
    CATEGORY_PAGE_NUMBER_IS_INVALID("The page number for categories must be greater than or equal to zero."),
    CATEGORY_PAGE_SIZE_NUMBER_IS_INVALID("The page number for retrieving categories must be greater than or equal to zero.");

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}