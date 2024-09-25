package com.emazon.stock.infraestructura.util;

public class InfrastructureConstants {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORITIES = "authorities";
    public static final String JWT_KEY_GENERATOR = "${security.jwt.key.private}";
    public static final String JWT_USER_GENERATOR = "${security.jwt.user.generator}";
    public static final String ACCESS_DENIED = "Access Denied: You do not have permission to access this resource.";
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized: You need to provide valid credentials to access this resource.";

    public static final String TEMPLATE_RESPONSE_ERROR = "{\"message\": \"%s\"}";


    public static final String ASCENDING = "ascending";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_NUMBER = "pageNumber";
    public static final String CATEGORY_ENTITY_LIST = "categoryEntityList";
    public static final String SIZE = "size";
    public static final String NUMBER = "number";

    public static final String PRODUCT_ENTITIES = "productEntities";

    public static final String EMPTY_IF_NULL_PRODUCT_ENTITY_PAGE_GET_CONTENT = "java(mapContentToEmptyIfNull(productEntityPage.getContent()))";
    public static final String EMPTY_IF_NULL_CATEGORY_ENTITY_PAGE_GET_CONTENT = "java(mapContentToEmptyIfNull(categoryEntityPage.getContent()))";
    public static final String EMPTY_IF_NULL_BRAND_ENTITY_PAGE_GET_CONTENT = "java(mapContentToEmptyIfNull(brandEntityPage.getContent()))";

    public static final String ID_PRODUCT = "id_product";
    public static final String BRAND_ID = "brand_id";
    public static final String PRODUCT_CATEGORY = "product_category";
    public static final String PRODUCT_ID = "product_id";
    public static final String CATEGORY_ID = "category_id";

    public static final String ID_CATEGORY = "id_category";
    public static final String MAX_CHAR_CATEGORY_NAME = "CHAR(50)";
    public static final String MAX_CHAR_CATEGORY_DESCRIPTION = "CHAR(90)";
    public static final String ID_BRAND = "id_brand";
    public static final String MAX_CHAR_BRAND_NAME = "CHAR(50)";
    public static final String MAX_CHAR_BRAND_DESCRIPTION = "CHAR(120)";
    public static final String BRAND_ENTITY = "brandEntity";
    private InfrastructureConstants() {
    }
}
