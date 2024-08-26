package com.emazon.stock.infraestructura.exceptionhandler;

public enum ExceptionResponse {
    CATEGORY_NOT_FOUND("No Category was found with that number"),
    CATEGORY_ALREADY_EXISTS("There is already a Category with that name"),
    CATEGORY_DESCRIPTION_TOO_LONG("The description has a maximum allowed characters of 90"),
    CATEGORY_NAME_TOO_LONG("The name has a maximum allowed characters of 50"),
    SORT_DIRECTION_IS_INVALID("Invalid sort direction provided"),
    CATEGORY_DESCRIPTION_REQUIRED("Category description cannot be null or empty."),
    CATEGORY_NAME_REQUIRED("Category name cannot be null or empty.");

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}