package com.emazon.stock.dominio.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void testCategoryConstructorAndGetters() {
        Long id = 1L;
        String name = "Hogar";
        String description = "Productos y accesorios para organizar, decorar y equipar cada rincón de tu hogar.";

        Category category = new Category(id, name, description);

        // Verificar que el constructor y los getters funcionan correctamente
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(description, category.getDescription());
    }

    @Test
    public void testSetters() {
        Category category = new Category(1L, "Hogar", "Descripción original");

        // Modificar los valores
        category.setId(2L);
        category.setName("Electrónica");
        category.setDescription("Descripción actualizada");

        // Verificar que los setters actualizan los valores correctamente
        assertEquals(2L, category.getId());
        assertEquals("Electrónica", category.getName());
        assertEquals("Descripción actualizada", category.getDescription());
    }
}
