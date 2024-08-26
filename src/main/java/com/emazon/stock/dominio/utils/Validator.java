package com.emazon.stock.dominio.utils;

import com.emazon.stock.dominio.exeption.*;
import com.emazon.stock.dominio.modelo.Category;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Validator {
    private static final Long MAX_DESCRIPTION_LENGTH = 90L;
    private static final Long MAX_NAME_LENGTH = 50L;
    private static final String[] PROPERTY_NAMES = { "Name", "Description" };
    private static final String[] TYPE_EXCEPTIONS = { "Required", "TooLong" };
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private Validator() {
    }

    private static final Map<String, Supplier<RuntimeException>> EXCEPTION_MAP = new HashMap<>();
    static {
        EXCEPTION_MAP.put("CategoryNameRequired", CategoryNameRequiredException::new);
        EXCEPTION_MAP.put("CategoryDescriptionRequired", CategoryDescriptionRequiredException::new);
        EXCEPTION_MAP.put("CategoryNameTooLong", CategoryNameTooLongException::new);
        EXCEPTION_MAP.put("CategoryDescriptionTooLong", CategoryDescriptionTooLongException::new);
    }

    /**
     * Valida el nombre y la descripción del objeto.
     * 
     * @param object El objeto puede ser una categoria y .....
     */
    public static void nameAndDescription(Object object) {

        String name = "";
        String description = "";
        if (object instanceof Category category) {
            name = category.getName();
            description = category.getDescription();
        }
        validateName(name, object.getClass().getSimpleName());
        validateDescription(description, object.getClass().getSimpleName());
    }

    /**
     * Validacion de string Sort
     * 
     * @param sort El objeto puede ser ASC O DESC
     */
    public static void sortDirection(String sort) {
        if (!(sort.equals(ASC) || sort.equals(DESC))) {
            throw new SortDirectionIsInvalidException();
        }
    }

    private static void validateName(String name, String modelName) {
        validateIsNotNullOrEmpty(name, modelName, PROPERTY_NAMES[0]);
        validateIsNotTooLong(name, modelName, PROPERTY_NAMES[0], MAX_NAME_LENGTH);
    }

    private static void validateDescription(String description, String modelName) {
        validateIsNotNullOrEmpty(description, modelName, PROPERTY_NAMES[1]);
        validateIsNotTooLong(description, modelName, PROPERTY_NAMES[1], MAX_DESCRIPTION_LENGTH);
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