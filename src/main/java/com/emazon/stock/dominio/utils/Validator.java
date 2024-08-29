package com.emazon.stock.dominio.utils;

import com.emazon.stock.dominio.exeption.*;
import com.emazon.stock.dominio.modelo.INamedDescriptiveModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.emazon.stock.dominio.utils.ConstantsDominio.PROPERTY_NAME;

public class Validator {
    private static final String CATEGORY = "Category";
    private static final String PROPERTY_DESCRIPTION = "Description";
    private static final Long MAX_NAME_LENGTH = 50L;
    private static final Long CATEGORY_MAX_DESCRIPTION_LENGTH = 90L;
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
        validateString(name, className, PROPERTY_NAME, MAX_NAME_LENGTH);
    }

    private static void validateDescription(String description,String className) {
        validateString(description, className, PROPERTY_DESCRIPTION,getMaxDescriptionLengthByClass(className));
    }
    private static Long getMaxDescriptionLengthByClass(String className){
        return switch (className) {
            case CATEGORY -> CATEGORY_MAX_DESCRIPTION_LENGTH;
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