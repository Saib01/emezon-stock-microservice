package com.emazon.stock.dominio.utils;

public class ConstantsDominio {

    public static final String CATEGORY = "Category";
    public static final String BRAND="Brand";

    public static final String PROPERTY_NAME = "Name";
    public static final String PROPERTY_DESCRIPTION = "Description";


    public static final Long CATEGORY_MAX_NAME_LENGTH = 50L;
    public static final Long BRAND_MAX_NAME_LENGTH = 50L;
    public static final Long CATEGORY_MAX_DESCRIPTION_LENGTH = 90L;
    public static final Long BRAND_MAX_DESCRIPTION_LENGTH = 120L;


    public static final String DIRECTION_ASC = "ASC";
    public static final String DIRECTION_DESC = "DESC";


    public static final String NOT_FOUND = "No %s was found with that number";
    public static final String ALREADY_EXISTS = "There is already a %s with that name";
    public static final String DESCRIPTION_TOO_LONG = "The %s description has a maximum allowed characters of %d";
    public static final String NAME_TOO_LONG = "The %s name has a maximum allowed characters of %d";
    public static final String DESCRIPTION_REQUIRED = "The %s description cannot be null or empty.";
    public static final String NAME_REQUIRED = "The %s name cannot be null or empty.";
    public static final String PAGE_SORT_IS_INVALID = "The sort direction for retrieving the %s page is invalid. Please use 'ASC' for ascending or 'DESC' for descending.";
    public static final String PAGE_NUMBER_IS_INVALID = "The page number for %s must be greater than or equal to zero.";
    public static final String PAGE_SIZE_IS_INVALID = "The page size to retrieve the %s page must be greater than or equal to one.";
    private ConstantsDominio() {
    }
}