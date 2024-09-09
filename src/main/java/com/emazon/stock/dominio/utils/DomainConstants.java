package com.emazon.stock.dominio.utils;

public class DomainConstants {

    public static final String CATEGORY = "Category";

    public static final Long CATEGORY_MAX_NAME_LENGTH = 50L;

    public static final Long CATEGORY_MAX_DESCRIPTION_LENGTH = 90L;
    public static final String BRAND="Brand";
    public static final Long BRAND_MAX_NAME_LENGTH = 50L;

    public static final Long BRAND_MAX_DESCRIPTION_LENGTH = 120L;

    public static final String PRODUCT="Product";
    public static final int ZERO=0;
    public static final String PROPERTY_AMOUNT="Amount";
    public static final String PROPERTY_PRICE = "Price";
    public static final String PROPERTY_ID = "Id";

    public static final String PROPERTY_NAME = "Name";
    public static final String PROPERTY_DESCRIPTION = "Description";
    public static final int PRODUCT_MAX_CATEGORY=3;
    public static final int PRODUCT_MIN_CATEGORY=1;

    private DomainConstants() {
    }
}