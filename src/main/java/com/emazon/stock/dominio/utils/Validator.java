package com.emazon.stock.dominio.utils;

import com.emazon.stock.dominio.exeption.brand.BrandNameTooLongException;
import com.emazon.stock.dominio.exeption.brand.BrandNameRequiredException;
import com.emazon.stock.dominio.exeption.brand.BrandDescriptionRequiredException;
import com.emazon.stock.dominio.exeption.brand.BrandDescriptionTooLongException;
import com.emazon.stock.dominio.exeption.brand.BrandAlreadyExistException;
import com.emazon.stock.dominio.exeption.category.CategoryNameTooLongException;
import com.emazon.stock.dominio.exeption.category.CategoryNameRequiredException;
import com.emazon.stock.dominio.exeption.category.CategoryDescriptionRequiredException;
import com.emazon.stock.dominio.exeption.category.CategoryDescriptionTooLongException;
import com.emazon.stock.dominio.exeption.category.CategoryAlreadyExistException;
import com.emazon.stock.dominio.modelo.INamedDescriptiveModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.emazon.stock.dominio.utils.ConstantsDominio.*;

public class Validator {
    private static final String[] TYPE_EXCEPTIONS = { "Exist","Required", "TooLong" };

    private Validator() {
    }

    private static final Map<String, Supplier<RuntimeException>> EXCEPTION_MAP = new HashMap<>();
    static {
        EXCEPTION_MAP.put("CategoryNameRequired", CategoryNameRequiredException::new);
        EXCEPTION_MAP.put("CategoryDescriptionRequired", CategoryDescriptionRequiredException::new);
        EXCEPTION_MAP.put("CategoryNameTooLong", CategoryNameTooLongException::new);
        EXCEPTION_MAP.put("CategoryDescriptionTooLong", CategoryDescriptionTooLongException::new);
        EXCEPTION_MAP.put("CategoryNameExist", CategoryAlreadyExistException::new);

        EXCEPTION_MAP.put("BrandNameRequired", BrandNameRequiredException::new);
        EXCEPTION_MAP.put("BrandDescriptionRequired", BrandDescriptionRequiredException::new);
        EXCEPTION_MAP.put("BrandNameTooLong", BrandNameTooLongException::new);
        EXCEPTION_MAP.put("BrandDescriptionTooLong", BrandDescriptionTooLongException::new);
        EXCEPTION_MAP.put("BrandNameExist", BrandAlreadyExistException::new);
    }

    public static void nameAndDescription(INamedDescriptiveModel object) {
        validateName(object.getName(), object.getClass().getSimpleName());
        validateDescription(object.getDescription(), object.getClass().getSimpleName());
    }

    public static void isNameAlreadyInUse(Boolean exist,INamedDescriptiveModel object){
        if (Boolean.TRUE.equals(exist)) {
            throw getExceptionForKey(object.getClass().getSimpleName(),PROPERTY_NAME, TYPE_EXCEPTIONS[0]);
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