package com.emazon.stock.utils;

import java.util.Arrays;
import java.util.List;

public class TestConstants {
    public static final long VALID_ID = 1L;
    public static final long INVALID_ID = 3841L;
    public static final String VALID_CATEGORY_NAME = "Electronics";
    public static final String INVALID_CATEGORY_NAME = "High-Performance Computing Systems and Advanced Computational Technologies";
    public static final String VALID_BRAND_NAME = "Luminix";
    public static final String INVALID_BRAND_NAME = "The Original and Genuine Brother’s All-Natural Fruit Crisps";
    public static final String VALID_BRAND_DESCRIPTION = "Marca innovadora de tecnología que ofrece soluciones de iluminación inteligente y eficiente para hogares y empresas.";
    public static final String INVALID_BRAND_DESCRIPTION = "Marca dedicada a la creación de productos de iluminación inteligente, eficiente y sostenible, diseñada para mejorar la calidad de vida a través de soluciones innovadoras que transforman la forma en que las personas interactúan con sus espacios, proporcionando control total desde cualquier dispositivo.";
    public static final String VALID_PRODUCT_NAME = "UltraClean 3000";
    public static final String VALID_PRODUCT_DESCRIPTION = "Aspirador UltraClean 3000: potente, con filtro HEPA y accesorios versátiles.";
    public static final Integer VALID_AMOUNT = 1000;
    public static final Double VALID_PRICE = 10000D;
    public static final String VALID_CATEGORY_DESCRIPTION = "Devices and gadgets.";
    public static final String INVALID_CATEGORY_DESCRIPTION = "Electronics involves devices and systems that control and manipulate electrical energy for various uses.";
    public static final String EMPTY_PROPERTY = "";
    public static final String CATEGORY_PROPERTY_NAME = "categoryName";
    public static final String INVALID_CATEGORY_PROPERTY_NAME = "ategoryName";
    public static final String NULL_PROPERTY = null;
    public static final int VALID_PAGE = 0;
    public static final int INVALID_PAGE = -5;
    public static final int VALID_SIZE = 10;
    public static final int INVALID_SIZE = -5;
    public static final int VALID_TOTAL_PAGES = 1;
    public static final int VALID_TOTAL_ELEMENTS = 10;
    public static final String INVALID_SORT_DIRECTION = "Assac";
    public static final Long VALID_ID_TWO=2L;
    public static final List<Long> VALID_LIST_PRODUCTS_IDS = Arrays.asList(1L, 2L);
    public static final List<Long> VALID_LIST_PRODUCTS_IDS_TWO = Arrays.asList(3L, 4L);
    public static final int ONE=1;
    private TestConstants() {
    }
}
