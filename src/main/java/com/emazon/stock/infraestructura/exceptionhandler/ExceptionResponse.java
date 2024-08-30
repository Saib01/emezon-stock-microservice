package com.emazon.stock.infraestructura.exceptionhandler;

import static com.emazon.stock.dominio.utils.ConstantsDominio.NOT_FOUND;
import static com.emazon.stock.dominio.utils.ConstantsDominio.ALREADY_EXISTS;
import static com.emazon.stock.dominio.utils.ConstantsDominio.DESCRIPTION_TOO_LONG;
import static com.emazon.stock.dominio.utils.ConstantsDominio.NAME_TOO_LONG;
import static com.emazon.stock.dominio.utils.ConstantsDominio.DESCRIPTION_REQUIRED;
import static com.emazon.stock.dominio.utils.ConstantsDominio.NAME_REQUIRED;
import static com.emazon.stock.dominio.utils.ConstantsDominio.PAGE_SORT_IS_INVALID;
import static com.emazon.stock.dominio.utils.ConstantsDominio.PAGE_NUMBER_IS_INVALID;
import static com.emazon.stock.dominio.utils.ConstantsDominio.PAGE_SIZE_IS_INVALID;
import static com.emazon.stock.dominio.utils.ConstantsDominio.CATEGORY_MAX_DESCRIPTION_LENGTH;
import static com.emazon.stock.dominio.utils.ConstantsDominio.CATEGORY_MAX_NAME_LENGTH;
import static com.emazon.stock.dominio.utils.ConstantsDominio.BRAND_MAX_DESCRIPTION_LENGTH;
import static com.emazon.stock.dominio.utils.ConstantsDominio.BRAND_MAX_NAME_LENGTH;
import static com.emazon.stock.dominio.utils.ConstantsDominio.CATEGORY;
import static com.emazon.stock.dominio.utils.ConstantsDominio.BRAND;
import static java.lang.String.format;

public enum ExceptionResponse {
    CATEGORY_NOT_FOUND(format(NOT_FOUND,CATEGORY)),
    CATEGORY_ALREADY_EXISTS(format(ALREADY_EXISTS,CATEGORY)),
    CATEGORY_DESCRIPTION_TOO_LONG(format(DESCRIPTION_TOO_LONG,CATEGORY,CATEGORY_MAX_DESCRIPTION_LENGTH)),
    CATEGORY_NAME_TOO_LONG(format(NAME_TOO_LONG,CATEGORY,CATEGORY_MAX_NAME_LENGTH)),
    CATEGORY_DESCRIPTION_REQUIRED(format(DESCRIPTION_REQUIRED,CATEGORY)),
    CATEGORY_NAME_REQUIRED(format(NAME_REQUIRED,CATEGORY)),
    CATEGORY_PAGE_SORT_DIRECTION_IS_INVALID(format(PAGE_SORT_IS_INVALID,CATEGORY)),
    CATEGORY_PAGE_NUMBER_IS_INVALID(format(PAGE_NUMBER_IS_INVALID,CATEGORY)),
    CATEGORY_PAGE_SIZE_NUMBER_IS_INVALID(format(PAGE_SIZE_IS_INVALID,CATEGORY)),
    BRAND_NOT_FOUND(format(NOT_FOUND,BRAND)),
    BRAND_ALREADY_EXISTS(format(ALREADY_EXISTS,BRAND)),
    BRAND_DESCRIPTION_TOO_LONG(format(DESCRIPTION_TOO_LONG,BRAND,BRAND_MAX_DESCRIPTION_LENGTH)),
    BRAND_NAME_TOO_LONG(format(NAME_TOO_LONG,BRAND,BRAND_MAX_NAME_LENGTH)),
    BRAND_DESCRIPTION_REQUIRED(format(DESCRIPTION_REQUIRED,BRAND)),
    BRAND_NAME_REQUIRED(format(NAME_REQUIRED,BRAND)),
    BRAND_PAGE_SORT_DIRECTION_IS_INVALID(format(PAGE_SORT_IS_INVALID,BRAND)),
    BRAND_PAGE_NUMBER_IS_INVALID(format(PAGE_NUMBER_IS_INVALID,BRAND)),
    BRAND_PAGE_SIZE_NUMBER_IS_INVALID(format(PAGE_SIZE_IS_INVALID,BRAND));
    private final String message;
    ExceptionResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
}