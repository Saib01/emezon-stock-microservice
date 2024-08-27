package com.emazon.stock.dominio.utils;

import com.emazon.stock.dominio.exeption.*;
import com.emazon.stock.dominio.modelo.INamedDescriptiveModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Validator {
    private static final Long MAX_DESCRIPTION_LENGTH = 90L;
    private static final Long MAX_NAME_LENGTH = 50L;
    private static final String PROPERTY_NAME = "Name";
    private static final String PROPERTY_DESCRIPTION = "Description";
    private static final String[] TYPE_EXCEPTIONS = { "Required", "TooLong" };

    private Validator() {
    }

    private static final Map<String, Supplier<RuntimeException>> EXCEPTION_MAP = new HashMap<>();
    static {
        EXCEPTION_MAP.put("CategoryNameRequired", CategoryNameRequiredException::new);
        EXCEPTION_MAP.put("CategoryDescriptionRequired", CategoryDescriptionRequiredException::new);
        EXCEPTION_MAP.put("CategoryNameTooLong", CategoryNameTooLongException::new);
        EXCEPTION_MAP.put("CategoryDescriptionTooLong", CategoryDescriptionTooLongException::new);
    }

    public static void nameAndDescription(INamedDescriptiveModel object) {
        validateName(object);
        validateDescription(object);
    }

    private static void validateName(INamedDescriptiveModel object) {
        validateString(object.getName(), object.getClass().getSimpleName(), PROPERTY_NAME, MAX_NAME_LENGTH);
    }

    private static void validateDescription(INamedDescriptiveModel object) {
        validateString(object.getDescription(), object.getClass().getSimpleName(), PROPERTY_DESCRIPTION,
                MAX_DESCRIPTION_LENGTH);
    }

    private static void validateString(String validateProperty, String modelName, String propertyName, Long maxLength) {
        validateIsNotNullOrEmpty(validateProperty, modelName, propertyName);
        validateIsNotTooLong(validateProperty, modelName, propertyName, maxLength);
    }

    private static void validateIsNotNullOrEmpty(String text, String modelName, String property) {
        if (text == null || text.trim().isEmpty()) {
            throw EXCEPTION_MAP.get(nameKeyExceptionString(modelName, property, TYPE_EXCEPTIONS[0])).get();
        }
    }

    private static void validateIsNotTooLong(String text, String modelName, String property, Long length) {
        if (text.length() > length) {
            throw EXCEPTION_MAP.get(nameKeyExceptionString(modelName, property, TYPE_EXCEPTIONS[1])).get();
        }
    }

    private static String nameKeyExceptionString(String modelName, String property, String typeException) {
        return new StringBuilder(modelName).append(property).append(typeException).toString();
    }

}