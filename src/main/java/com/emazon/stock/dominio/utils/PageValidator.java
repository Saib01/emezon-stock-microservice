package com.emazon.stock.dominio.utils;

import com.emazon.stock.dominio.exeption.category.CategoryPageNumberIsInvalidException;
import com.emazon.stock.dominio.exeption.category.CategoryPageSizeIsInvalidException;
import com.emazon.stock.dominio.exeption.category.CategoryPageSortDirectionIsInvalidException;
import com.emazon.stock.dominio.exeption.brand.BrandPageNumberIsInvalidException;
import com.emazon.stock.dominio.exeption.brand.BrandPageSizeIsInvalidException;
import com.emazon.stock.dominio.exeption.brand.BrandPageSortDirectionIsInvalidException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.emazon.stock.dominio.utils.ConstantsDominio.DIRECTION_ASC;
import static com.emazon.stock.dominio.utils.ConstantsDominio.DIRECTION_DESC;

public class PageValidator {
    private static final String PROPERTY_PAGE_SORT_DIRECTION = "PageSortDirection";
    private static final String PROPERTY_PAGE_NUMBER = "PageNumber";
    private static final String PROPERTY_PAGE_SIZE = "PageSize";
    private static final String TYPE_EXCEPTIONS = "IsInvalid";
    private static final Map<String, Supplier<RuntimeException>> EXCEPTION_MAP = new HashMap<>();
    static {
        EXCEPTION_MAP.put("CategoryPageSortDirectionIsInvalid", CategoryPageSortDirectionIsInvalidException::new);
        EXCEPTION_MAP.put("CategoryPageNumberIsInvalid", CategoryPageNumberIsInvalidException::new);
        EXCEPTION_MAP.put("CategoryPageSizeIsInvalid", CategoryPageSizeIsInvalidException::new);
        EXCEPTION_MAP.put("BrandPageSortDirectionIsInvalid", BrandPageSortDirectionIsInvalidException::new);
        EXCEPTION_MAP.put("BrandPageNumberIsInvalid", BrandPageNumberIsInvalidException::new);
        EXCEPTION_MAP.put("BrandPageSizeIsInvalid", BrandPageSizeIsInvalidException::new);
    }
    private PageValidator() {
    }
    public static void parameters(int page, int size, String sortDirection,String modelName) {
        validateSortDirection(sortDirection,modelName);
        validatePage(page,modelName);
        validateSize(size,modelName);
    }

    private static void validateSortDirection(String sortDirection,String modelName){
        if (!(sortDirection.equalsIgnoreCase(DIRECTION_ASC) || sortDirection.equalsIgnoreCase(DIRECTION_DESC))) {
            throw getExceptionForKey(modelName,PROPERTY_PAGE_SORT_DIRECTION, TYPE_EXCEPTIONS);
        }
    }
    private static void validatePage(int page,String modelName){
        if (page<0) {
            throw getExceptionForKey(modelName,PROPERTY_PAGE_NUMBER, TYPE_EXCEPTIONS);
        }
    }
    private static void validateSize(int size,String modelName){
        if (size<=0) {
            throw getExceptionForKey(modelName,PROPERTY_PAGE_SIZE, TYPE_EXCEPTIONS);
        }
    }
    private static RuntimeException getExceptionForKey(String modelName, String property, String typeException) {
        String key = String.format("%s%s%s", modelName, property, typeException);
        return EXCEPTION_MAP.get(key).get();
    }

}
