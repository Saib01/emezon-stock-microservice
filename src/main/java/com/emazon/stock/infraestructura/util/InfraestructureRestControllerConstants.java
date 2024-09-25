package com.emazon.stock.infraestructura.util;

import static com.emazon.stock.dominio.utils.DomainConstants.*;

public class InfraestructureRestControllerConstants {

    public static final String RESPONSE_CODE_CREATED = "201";
    public static final String RESPONSE_CODE_SUCCESS = "200";
    public static final String RESPONSE_CODE_CONFLICT = "409";
    public static final String RESPONSE_CODE_BAD_REQUEST = "400";
    public static final String RESPONSE_CODE_NOT_FOUND = "404";

    public static final String SUMMARY_ADD_A_NEW_PRODUCT = "Add a new Product";
    public static final String SUMMARY_GET_A_PRODUCT_BY_NUMBER = "Get a Product by number";
    public static final String SUMMARY_GET_A_PAGINATED_LIST_OF_PRODUCTS = "Get a paginated list of products";
    public static final String SUMMARY_INCREASE_SUPPLY_STOCK = "Increase supply stock";
    public static final String SUMMARY_VALIDATE_THE_LIMIT_OF_PRODUCTS_PER_CATEGORY = "Validate the limit of products per category";
    public static final String SUMMARY_GET_A_PAGINATED_LIST_OF_CATEGORIES = "Get a paginated list of categories";
    public static final String SUMMARY_ADD_A_NEW_CATEGORY = "Add a new category";
    public static final String SUMMARY_GET_A_CATEGORY_BY_NUMBER = "Get a category by number";
    public static final String SUMMARY_ADD_A_NEW_BRAND = "Add a new brand";
    public static final String SUMMARY_GET_A_PAGINATED_LIST_OF_BRANDS = "Get a paginated list of brands";
    public static final String SUMMARY_GET_A_BRAND_BY_NUMBER = "Get a brand by number";

    public static final String RESPONSE_DESCRIPTION_PRODUCT_CREATED = "Product created";
    public static final String INCREASES_THE_STOCK_OF_A_SUPPLY_BY_A_GIVEN_INCREMENT = "Increases the stock of a supply by a given increment";
    public static final String PRODUCT_FOUND = "Product found";
    public static final String LIST_OF_PRODUCTS = "List of products";
    public static final String STOCK_UPDATED_SUCCESSFULLY = "Stock updated successfully";
    public static final String VALIDATION_COMPLETED_SUCCESSFULLY = "Validation completed successfully";
    public static final String CATEGORY_CREATED = "Category created";
    public static final String LIST_OF_CATEGORIES = "List of categories";
    public static final String CATEGORY_FOUND = "Category found";
    public static final String BRAND_CREATED = "Brand created";
    public static final String LIST_OF_BRANDS = "List of brands";
    public static final String BRAND_FOUND = "Brand found";

    public static final String CONFLICT_PRODUCT_NOT_CREATED_DESCRIPTION = "Product already exists";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String INVALID_INPUT_DATA = "Invalid input data";
    public static final String NOT_FOUND = "Category not found";
    public static final String CATEGORY_ALREADY_EXISTS = "Category already exists";
    public static final String BRAND_NOT_FOUND = "Brand not found";
    public static final String BRAND_ALREADY_EXISTS = "Brand already exists";

    public static final String SORT_BY = "sortBy";
    public static final String SORT_DIRECTION = "sortDirection";
    public static final String PAGE = "page";
    public static final String DEFAULT_SORT_BY = "productName";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";

    public static final String API_BASE = "/api/%s/";
    public static final String ADMIN = "ADMIN";
    public static final String AUX_BODEGA = "AUX_BODEGA";
    public static final String API_CATEGORY = String.format(API_BASE, CATEGORY);
    public static final String API_BRAND = String.format(API_BASE, BRAND);
    public static final String API_PRODUCT = String.format(API_BASE, PRODUCT);
    public static final String API_PRODUCT_ADD_SUPPLY = API_PRODUCT.concat("*/add-supply");

    private InfraestructureRestControllerConstants() {

    }
}
