package com.emazon.stock.infraestructura.exceptionhandler;

public enum ExceptionResponse {
    CATEGORY_NOT_FOUND("No Category was found with that number"),
    CATEGORY_ALREADY_EXISTS("There is already a Category with that name"),
    CATEGORY_DESCRIPTION_TOO_LONG("The Category description has a maximum allowed characters of 90"),
    CATEGORY_NAME_TOO_LONG("The Category name has a maximum allowed characters of 50"),
    CATEGORY_DESCRIPTION_REQUIRED("Category description cannot be null or empty."),
    CATEGORY_NAME_REQUIRED("Category name cannot be null or empty."),
    CATEGORY_PAGE_SORT_DIRECTION_IS_INVALID("The sort direction for retrieving categories is invalid. Please use 'ASC' for ascending or 'DESC' for descending."),
    CATEGORY_PAGE_NUMBER_IS_INVALID("The page number for categories must be greater than or equal to zero."),
    CATEGORY_PAGE_SIZE_NUMBER_IS_INVALID("The page number for retrieving categories must be greater than or equal to zero."),

    BRAND_NOT_FOUND("No Brand was found with that number"),
    BRAND_ALREADY_EXISTS("There is already a Brand with that name"),
    BRAND_DESCRIPTION_TOO_LONG("The Brand description has a maximum allowed characters of 90"),
    BRAND_NAME_TOO_LONG("The Brand name has a maximum allowed characters of 50"),
    BRAND_DESCRIPTION_REQUIRED("Brand description cannot be null or empty."),
    BRAND_NAME_REQUIRED("Brand name cannot be null or empty.");


    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}