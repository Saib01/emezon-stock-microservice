package com.emazon.stock.dominio.modelo;

import static com.emazon.stock.dominio.constants.GlobalConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {
    @DisplayName("Verify constructor and getters")
    @Test
    void testCategoryConstructorAndGetters() {
        Category category = new Category(VALID_ID, VALID_NAME, VALID_DESCRIPTION);
        assertEquals(VALID_ID, category.getId());
        assertEquals(VALID_NAME, category.getName());
        assertEquals(VALID_DESCRIPTION, category.getDescription());
    }

    @DisplayName("Verify setters")
    @Test
    void testSetters() {
        Category category = new Category(VALID_ID, VALID_NAME, VALID_DESCRIPTION);
        category.setId(2L);
        category.setName("Electrónica");
        category.setDescription("Descripción actualizada");
        assertEquals(2L, category.getId());
        assertEquals("Electrónica", category.getName());
        assertEquals("Descripción actualizada", category.getDescription());
    }
}
