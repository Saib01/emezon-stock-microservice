package com.emazon.stock.dominio.utils;

import com.emazon.stock.dominio.exeption.brand.*;
import com.emazon.stock.dominio.exeption.category.*;
import com.emazon.stock.dominio.exeption.product.*;
import com.emazon.stock.dominio.modelo.*;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.spi.IModelPersistencePort;

import java.util.*;
import java.util.function.Supplier;

import static com.emazon.stock.dominio.utils.DomainConstants.*;

public class Validator {
    private static final String[] TYPE_EXCEPTIONS = { "Exist","Required", "TooLong","Invalid" };

    private Validator() {
    }

    private static final Map<String, Supplier<RuntimeException>> EXCEPTION_MAP = new HashMap<>();
    static {
        EXCEPTION_MAP.put("CategoryNameRequired", CategoryNameRequiredException::new);
        EXCEPTION_MAP.put("CategoryDescriptionRequired", CategoryDescriptionRequiredException::new);
        EXCEPTION_MAP.put("CategoryNameTooLong", CategoryNameTooLongException::new);
        EXCEPTION_MAP.put("CategoryDescriptionTooLong", CategoryDescriptionTooLongException::new);
        EXCEPTION_MAP.put("CategoryNameExist", CategoryAlreadyExistException::new);
        EXCEPTION_MAP.put("CategoryIdInvalid", CategoryNotFoundException::new);

        EXCEPTION_MAP.put("BrandNameRequired", BrandNameRequiredException::new);
        EXCEPTION_MAP.put("BrandDescriptionRequired", BrandDescriptionRequiredException::new);
        EXCEPTION_MAP.put("BrandNameTooLong", BrandNameTooLongException::new);
        EXCEPTION_MAP.put("BrandDescriptionTooLong", BrandDescriptionTooLongException::new);
        EXCEPTION_MAP.put("BrandNameExist", BrandAlreadyExistException::new);
        EXCEPTION_MAP.put("BrandIdInvalid", BrandNotFoundException::new);

        EXCEPTION_MAP.put("ProductNameRequired", ProductNameRequiredException::new);
        EXCEPTION_MAP.put("ProductDescriptionRequired", ProductDescriptionRequiredException::new);
        EXCEPTION_MAP.put("ProductNameExist", ProductAlreadyExistException::new);
        EXCEPTION_MAP.put("ProductAmountInvalid", ProductAmountInvalidException::new);
        EXCEPTION_MAP.put("ProductPriceInvalid", ProductPriceInvalidException::new);
    }

    public static void nameAndDescription(ModelBase object) {
        validateName(object.getName(), object.getClass().getSimpleName());
        validateDescription(object.getDescription(), object.getClass().getSimpleName());
    }
    public static void validateProduct(Product product, IBrandPersistencePort brandPersistencePort,
                                       ICategoryPersistencePort categoryPersistencePort){

        validateIsNotNullOrEmpty(product.getName(),PRODUCT,PROPERTY_NAME);
        validateIsNotNullOrEmpty(product.getDescription(),PRODUCT,PROPERTY_DESCRIPTION);
        validateIsGreaterThanZero(Double.valueOf(product.getAmount()),PRODUCT,PROPERTY_AMOUNT);
        validateIsGreaterThanZero(product.getPrice(),PRODUCT,PROPERTY_PRICE);
        validateExistsInDataBase(brandPersistencePort, product.getBrand());
        validateNonDuplicateAndSize(product.getCategoryList());
        product.getCategoryList().stream()
                .forEach(category -> validateExistsInDataBase(categoryPersistencePort,category));
    }
    public static void validateNameIsAlreadyInUse(IModelPersistencePort modelPersistencePort, ModelBase object){
        if (modelPersistencePort.existsByName(object.getName())) {
            throw getExceptionForKey(object.getClass().getSimpleName(),PROPERTY_NAME, TYPE_EXCEPTIONS[0]);
        }
    }

    private static void validateExistsInDataBase(IModelPersistencePort modelPersistencePort, ModelBase object) {
        if (!modelPersistencePort.existsById(object.getId())) {
            throw getExceptionForKey(object.getClass().getSimpleName(), PROPERTY_ID, TYPE_EXCEPTIONS[3]);
        }
    }
    private static void validateNonDuplicateAndSize(List<Category> categoryList){
        if( ! ( categoryList.size() >=PRODUCT_MIN_CATEGORY && categoryList.size() <=PRODUCT_MAX_CATEGORY ) ){
            throw new CategoryListSizeException();
        }
        Set<Long> uniqueCategoryIds = new HashSet<>();
        categoryList.stream()
                .map(Category::getId)
                .filter(id-> id != null)
                .forEach(uniqueCategoryIds::add);
        if(uniqueCategoryIds.size()!= categoryList.size()){
            throw new CategoryDuplicateException();
        }
    }
    private static void validateIsGreaterThanZero(Double number,String modelName, String property){
        if (number<=ZERO) {
            throw getExceptionForKey(modelName, property, TYPE_EXCEPTIONS[3]);
        }
    }
    private static void validateName(String name,String className) {
        validateString(name, className, PROPERTY_NAME, getMaxNameLengthByClass(className));
    }

    private static void validateDescription(String description,String className) {
        validateString(description, className, PROPERTY_DESCRIPTION,getMaxDescriptionLengthByClass(className));
    }
    private static Long getMaxDescriptionLengthByClass(String className){
        return switch (className) {
            case CATEGORY -> CATEGORY_MAX_DESCRIPTION_LENGTH;
            case BRAND -> BRAND_MAX_DESCRIPTION_LENGTH;
            default -> throw new IllegalArgumentException("Unknown class name: " + className);
        };
    }
    private static Long getMaxNameLengthByClass(String className){
        return switch (className) {
            case CATEGORY -> CATEGORY_MAX_NAME_LENGTH;
            case BRAND -> BRAND_MAX_NAME_LENGTH;
            default -> throw new IllegalArgumentException("Unknown class name: " + className);
        };
    }

    private static void validateString(String validateProperty, String modelName, String propertyName, Long maxLength) {
        validateIsNotNullOrEmpty(validateProperty, modelName, propertyName);
        validateIsNotTooLong(validateProperty, modelName, propertyName, maxLength);
    }

    private static void validateIsNotNullOrEmpty(String text, String modelName, String property) {
        if ( text == null  || text.trim().isEmpty() ) {
            throw getExceptionForKey(modelName, property, TYPE_EXCEPTIONS[1]);
        }
    }
    private static void validateIsNotTooLong(String text, String modelName, String property, Long length) {
        if (text.length() > length) {
            throw getExceptionForKey(modelName, property, TYPE_EXCEPTIONS[2]);
        }
    }

    private static RuntimeException getExceptionForKey(String modelName, String property, String typeException) {
        String key = String.format("%s%s%s", modelName, property, typeException);
        return EXCEPTION_MAP.get(key).get();
    }

}