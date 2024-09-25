package com.emazon.stock.dominio.utils;

import com.emazon.stock.dominio.exeption.brand.BrandPageNumberIsInvalidException;
import com.emazon.stock.dominio.exeption.brand.BrandPageSizeIsInvalidException;
import com.emazon.stock.dominio.exeption.brand.BrandPageSortDirectionIsInvalidException;
import com.emazon.stock.dominio.exeption.category.CategoryPageNumberIsInvalidException;
import com.emazon.stock.dominio.exeption.category.CategoryPageSizeIsInvalidException;
import com.emazon.stock.dominio.exeption.category.CategoryPageSortDirectionIsInvalidException;
import com.emazon.stock.dominio.exeption.product.ProductPageNumberIsInvalidException;
import com.emazon.stock.dominio.exeption.product.ProductPageSizeIsInvalidException;
import com.emazon.stock.dominio.exeption.product.ProductPageSortByIsInvalidException;
import com.emazon.stock.dominio.exeption.product.ProductPageSortDirectionIsInvalidException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.emazon.stock.dominio.exeption.ExceptionResponse.*;
import static com.emazon.stock.dominio.utils.Direction.*;
import static com.emazon.stock.dominio.utils.DomainConstants.*;
import static java.lang.String.format;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

public class PageValidator {
    private static final String PROPERTY_PAGE_SORT_DIRECTION = "PageSortDirection";
    private static final String PROPERTY_PAGE_NUMBER = "PageNumber";
    private static final String PROPERTY_PAGE_SIZE = "PageSize";
    private static final String TYPE_EXCEPTIONS = "IsInvalid";
    private static final String EMPTY_STRING = "";
    private static final String KEY_FORMAT = "%s%s%s";
    private static final Map<String, Supplier<RuntimeException>> EXCEPTION_MAP = new HashMap<>();

    static {
        EXCEPTION_MAP.put("CategoryPageSortDirectionIsInvalid", () -> new CategoryPageSortDirectionIsInvalidException(CATEGORY_PAGE_SORT_DIRECTION_IS_INVALID));
        EXCEPTION_MAP.put("CategoryPageNumberIsInvalid", () -> new CategoryPageNumberIsInvalidException(CATEGORY_PAGE_NUMBER_IS_INVALID));
        EXCEPTION_MAP.put("CategoryPageSizeIsInvalid", () -> new CategoryPageSizeIsInvalidException(CATEGORY_PAGE_SIZE_NUMBER_IS_INVALID));

        EXCEPTION_MAP.put("BrandPageSortDirectionIsInvalid", () -> new BrandPageSortDirectionIsInvalidException(BRAND_PAGE_SORT_DIRECTION_IS_INVALID));
        EXCEPTION_MAP.put("BrandPageNumberIsInvalid", () -> new BrandPageNumberIsInvalidException(BRAND_PAGE_NUMBER_IS_INVALID));
        EXCEPTION_MAP.put("BrandPageSizeIsInvalid", () -> new BrandPageSizeIsInvalidException(BRAND_PAGE_SIZE_NUMBER_IS_INVALID));

        EXCEPTION_MAP.put("ProductPageSortDirectionIsInvalid", () -> new ProductPageSortDirectionIsInvalidException(PRODUCT_PAGE_SORT_DIRECTION_IS_INVALID));
        EXCEPTION_MAP.put("ProductPageNumberIsInvalid", () -> new ProductPageNumberIsInvalidException(PRODUCT_PAGE_NUMBER_IS_INVALID));
        EXCEPTION_MAP.put("ProductPageSizeIsInvalid", () -> new ProductPageSizeIsInvalidException(PRODUCT_PAGE_SIZE_NUMBER_IS_INVALID));
        EXCEPTION_MAP.put("ProductPageSortByIsInvalid", () -> new ProductPageSortByIsInvalidException(PRODUCT_PAGE_SORT_BY_IS_INVALID));
    }

    private PageValidator() {
    }

    public static void parameters(int page, int size, String sortDirection, String modelName) {
        validateSortDirection(sortDirection, modelName);
        validatePage(page, modelName);
        validateSize(size, modelName);
    }

    public static List<String> sortBy(String sortBy) {
        String comparator = sortBy.replaceAll(PROPERTY_NAME, EMPTY_STRING);
        return switch (comparator) {
            case CATEGORY -> getSortProperties(TWO.intValue(), ZERO, ONE.intValue());
            case BRAND -> getSortProperties(ONE.intValue(), ZERO, TWO.intValue());
            case PRODUCT -> getSortProperties(ZERO, ONE.intValue(), TWO.intValue());
            default -> throw new ProductPageSortByIsInvalidException(PRODUCT_PAGE_SORT_BY_IS_INVALID);
        };
    }
    private static List<String> getSortProperties(int... indices) {
        return Arrays.stream(indices)
                .mapToObj(index -> SORT_PROPERTIES[index])
                .toList();
    }
    private static void validateSortDirection(String sortDirection, String modelName) {
        if (!(sortDirection.equalsIgnoreCase(ASC) || sortDirection.equalsIgnoreCase(DESC))) {
            throw getExceptionForKey(modelName, PROPERTY_PAGE_SORT_DIRECTION);
        }
    }

    private static void validatePage(int page, String modelName) {
        if (page < ZERO) {
            throw getExceptionForKey(modelName, PROPERTY_PAGE_NUMBER);
        }
    }

    private static void validateSize(int size, String modelName) {
        if (size <= ZERO) {
            throw getExceptionForKey(modelName, PROPERTY_PAGE_SIZE);
        }
    }

    private static RuntimeException getExceptionForKey(String modelName, String property) {
        String key = format(KEY_FORMAT, modelName, property, TYPE_EXCEPTIONS);
        return EXCEPTION_MAP.get(key).get();
    }

}
