package com.emazon.stock.dominio.utils;

import com.emazon.stock.dominio.exeption.brand.*;
import com.emazon.stock.dominio.exeption.category.*;
import com.emazon.stock.dominio.exeption.product.*;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.spi.IModelPersistencePort;

import java.util.*;
import java.util.function.Supplier;

import static com.emazon.stock.dominio.exeption.ExceptionResponse.*;
import static com.emazon.stock.dominio.utils.DomainConstants.*;

public class Validator {
    public static final String UNKNOWN_CLASS_NAME = "Unknown class name: ";
    public static final String FORMAT = "%s%s%s";
    public static final String SIMPLE_NAME_PRODUCT = Product.class.getSimpleName();
    private static final String[] TYPE_EXCEPTIONS = {"Exist", "Required", "TooLong", "Invalid"};
    private static final int INDEX_EXIST = 0;
    private static final int INDEX_REQUIRED = 1;
    private static final int INDEX_TOO_LONG = 2;
    private static final int INDEX_INVALID = 3;
    private static final Map<String, Supplier<RuntimeException>> EXCEPTION_MAP = new HashMap<>();

    static {
        EXCEPTION_MAP.put("CategoryNameRequired", () -> new CategoryNameRequiredException(CATEGORY_NAME_REQUIRED));
        EXCEPTION_MAP.put("CategoryDescriptionRequired", () -> new CategoryDescriptionRequiredException(CATEGORY_DESCRIPTION_REQUIRED));
        EXCEPTION_MAP.put("CategoryNameTooLong", () -> new CategoryNameTooLongException(CATEGORY_NAME_TOO_LONG));
        EXCEPTION_MAP.put("CategoryDescriptionTooLong", () -> new CategoryDescriptionTooLongException(CATEGORY_DESCRIPTION_TOO_LONG));
        EXCEPTION_MAP.put("CategoryNameExist", () -> new CategoryAlreadyExistException(CATEGORY_ALREADY_EXISTS));
        EXCEPTION_MAP.put("CategoryIdInvalid", () -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

        EXCEPTION_MAP.put("BrandNameRequired", () -> new BrandNameRequiredException(BRAND_NAME_REQUIRED));
        EXCEPTION_MAP.put("BrandDescriptionRequired", () -> new BrandDescriptionRequiredException(BRAND_DESCRIPTION_REQUIRED));
        EXCEPTION_MAP.put("BrandNameTooLong", () -> new BrandNameTooLongException(BRAND_NAME_TOO_LONG));
        EXCEPTION_MAP.put("BrandDescriptionTooLong", () -> new BrandDescriptionTooLongException(BRAND_DESCRIPTION_TOO_LONG));
        EXCEPTION_MAP.put("BrandNameExist", () -> new BrandAlreadyExistException(BRAND_ALREADY_EXISTS));
        EXCEPTION_MAP.put("BrandIdInvalid", () -> new BrandNotFoundException(BRAND_NOT_FOUND));

        EXCEPTION_MAP.put("ProductNameRequired", () -> new ProductNameRequiredException(PRODUCT_NAME_REQUIRED));
        EXCEPTION_MAP.put("ProductDescriptionRequired", () -> new ProductDescriptionRequiredException(PRODUCT_DESCRIPTION_REQUIRED));
        EXCEPTION_MAP.put("ProductNameExist", () -> new ProductAlreadyExistException(PRODUCT_ALREADY_EXISTS));
        EXCEPTION_MAP.put("ProductAmountInvalid", () -> new ProductAmountInvalidException(PRODUCT_AMOUNT_GREATER_THAN_ZERO));
        EXCEPTION_MAP.put("ProductPriceInvalid", () -> new ProductPriceInvalidException(PRODUCT_PRICE_GREATER_THAN_ZERO));
        EXCEPTION_MAP.put("ProductIdInvalid", () -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    private Validator() {
    }

    public static void nameAndDescription(ModelBase object) {
        validateName(object.getName(), object.getClass().getSimpleName());
        validateDescription(object.getDescription(), object.getClass().getSimpleName());
    }

    public static void validateProduct(Product product, IBrandPersistencePort brandPersistencePort,
                                       ICategoryPersistencePort categoryPersistencePort) {

        validateIsNotNullOrEmpty(product.getName(), SIMPLE_NAME_PRODUCT, PROPERTY_NAME);
        validateIsNotNullOrEmpty(product.getDescription(), SIMPLE_NAME_PRODUCT, PROPERTY_DESCRIPTION);
        validateIsGreaterThanZero(product.getAmount(), PROPERTY_AMOUNT);
        validateIsGreaterThanZero(product.getPrice(), PROPERTY_PRICE);
        validateExistsInDataBase(brandPersistencePort, product.getBrand());
        validateNonDuplicateAndSize(product.getCategoryList());
        product.getCategoryList().forEach(category -> validateExistsInDataBase(categoryPersistencePort, category));
    }

    public static void validateNameIsAlreadyInUse(IModelPersistencePort modelPersistencePort, ModelBase object) {
        if (modelPersistencePort.existsByName(object.getName())) {
            throw getExceptionForKey(object.getClass().getSimpleName(), PROPERTY_NAME, TYPE_EXCEPTIONS[INDEX_EXIST]);
        }
    }

    private static void validateExistsInDataBase(IModelPersistencePort modelPersistencePort, ModelBase object) {
        if (object.getId() == null || !modelPersistencePort.existsById(object.getId())) {
            throw getExceptionForKey(object.getClass().getSimpleName(), PROPERTY_ID, TYPE_EXCEPTIONS[INDEX_INVALID]);
        }
    }

    private static void validateNonDuplicateAndSize(List<Category> categoryList) {
        if (!(!categoryList.isEmpty() && categoryList.size() <= PRODUCT_MAX_CATEGORY)) {
            throw new CategoryListSizeException(CATEGORY_LIST_SIZE);
        }
        Set<Long> uniqueCategoryIds = new HashSet<>();
        categoryList.stream()
                .map(Category::getId)
                .forEach(uniqueCategoryIds::add);
        if (uniqueCategoryIds.size() != categoryList.size()) {
            throw new CategoryDuplicateException(CATEGORY_DUPLICATE);
        }
    }

    public static <T extends Number> void validateIsGreaterThanZero(T number, String property) {
        if (number == null || number.doubleValue() <= ZERO) {
            throw getExceptionForKey(SIMPLE_NAME_PRODUCT, property, TYPE_EXCEPTIONS[INDEX_INVALID]);
        }
    }

    private static void validateName(String name, String className) {
        validateString(name, className, PROPERTY_NAME, getMaxNameLengthByClass(className));
    }

    private static void validateDescription(String description, String className) {
        validateString(description, className, PROPERTY_DESCRIPTION, getMaxDescriptionLengthByClass(className));
    }

    private static Long getMaxDescriptionLengthByClass(String className) {
        return switch (className.toLowerCase()) {
            case CATEGORY -> CATEGORY_MAX_DESCRIPTION_LENGTH;
            case BRAND -> BRAND_MAX_DESCRIPTION_LENGTH;
            default -> throw new IllegalArgumentException(UNKNOWN_CLASS_NAME + className);
        };
    }

    private static Long getMaxNameLengthByClass(String className) {
        return switch (className.toLowerCase()) {
            case CATEGORY -> CATEGORY_MAX_NAME_LENGTH;
            case BRAND -> BRAND_MAX_NAME_LENGTH;
            default -> throw new IllegalArgumentException(UNKNOWN_CLASS_NAME + className);
        };
    }

    private static void validateString(String validateProperty, String modelName, String propertyName, Long maxLength) {
        validateIsNotNullOrEmpty(validateProperty, modelName, propertyName);
        validateIsNotTooLong(validateProperty, modelName, propertyName, maxLength);
    }

    private static void validateIsNotNullOrEmpty(String text, String modelName, String property) {
        if (text == null || text.trim().isEmpty()) {
            throw getExceptionForKey(modelName, property, TYPE_EXCEPTIONS[INDEX_REQUIRED]);
        }
    }

    private static void validateIsNotTooLong(String text, String modelName, String property, Long length) {
        if (text.length() > length) {
            throw getExceptionForKey(modelName, property, TYPE_EXCEPTIONS[INDEX_TOO_LONG]);
        }
    }

    private static RuntimeException getExceptionForKey(String modelName, String property, String typeException) {
        String key = String.format(FORMAT, modelName, property, typeException);
        return EXCEPTION_MAP.get(key).get();
    }

}