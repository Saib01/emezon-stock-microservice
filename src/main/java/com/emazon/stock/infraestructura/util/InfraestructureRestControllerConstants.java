package com.emazon.stock.infraestructura.util;

import static com.emazon.stock.dominio.utils.DomainConstants.*;

public class InfraestructureRestControllerConstants {

    public static final String RESPONSE_CODE_CREATED = "201";
    public static final String RESPONSE_CODE_SUCCESS = "200";
    public static final String RESPONSE_CODE_CONFLICT = "409";
    public static final String RESPONSE_CODE_BAD_REQUEST = "400";
    public static final String RESPONSE_CODE_NOT_FOUND = "404";
    public static final String RESPONSE_CODE_INTERNAL_SERVER = "500";

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

    public static final String VALIDATE_IF_THE_BRAND_NAME_IS_AVAILABLE = "Validate if the brand name is available";
    public static final String BRAND_NAME_IS_AVAILABLE = "Brand name is available";
    public static final String INVALID_BRAND_NAME_FORMAT = "Invalid brand name format";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";

    public static final String GET_PAGINATED_LIST_OF_PRODUCTS_IN_SHOPPING_CART = "Get paginated list of products in shopping cart";
    public static final String RETRIEVE_A_PAGINATED_LIST_OF_PRODUCTS_IN_THE_SHOPPING_CART_BASED_ON_THE_PROVIDED_LIST_REQUEST = "Retrieve a paginated list of products in the shopping cart based on the provided list request.";
    public static final String SUCCESSFULLY_RETRIEVED_PAGINATED_PRODUCT_LIST = "Successfully retrieved paginated product list";
    public static final String INVALID_REQUEST_DATA = "Invalid request data";
    public static final String INTERNAL_SERVER_ERROR1 = "Internal server error";
    public static final String REDUCE_STOCK_AFTER_PURCHASE = "Reduce stock after purchase";
    public static final String STOCK_REDUCED_SUCCESSFULLY = "Stock reduced successfully";
    public static final String INVALID_PRODUCT_REQUEST_DATA = "Invalid product request data";
    public static final String INTERNAL_SERVER_ERROR_WHILE_REDUCING_STOCK = "Internal server error while reducing stock";
    public static final String RESTORE_STOCK_TO_PREVIOUS_STATE = "Restore stock to previous state";
    public static final String STOCK_RESTORED_SUCCESSFULLY = "Stock restored successfully";
    public static final String INVALID_PRODUCT_REQUEST_DATA1 = "Invalid product request data";
    public static final String INTERNAL_SERVER_ERROR_WHILE_RESTORING_STOCK = "Internal server error while restoring stock";
    public static final String STOCK_RESTORATION_COMPLETED = "Stock restoration completed.";
    public static final String VALIDATE_IF_THE_PRODUCT_NAME_IS_AVAILABLE = "Validate if the product name is available";
    public static final String PRODUCT_NAME_IS_AVAILABLE = "Product name is available";
    public static final String INVALID_PRODUCT_NAME_FORMAT = "Invalid product name format";
    public static final String INTERNAL_SERVER_ERROR2 = "Internal server error";

    private InfraestructureRestControllerConstants() {

    }
}
