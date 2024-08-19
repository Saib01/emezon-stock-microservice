package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategory() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        // Ejecutar el método de la lógica de negocio
        categoryUseCase.saveCategory(category);

        // Verificar que el método saveCategory fue llamado en el puerto de persistencia
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    void testGetAllCategory() {
        List<Category> expectedCategories = Arrays.asList(
                new Category(1L, "Books", "All kinds of books"),
                new Category(2L, "Electronics", "Devices and gadgets")
        );

        // Mockear el comportamiento del puerto de persistencia
        when(categoryPersistencePort.getAllCategory()).thenReturn(expectedCategories);

        // Ejecutar el método de la lógica de negocio
        List<Category> actualCategories = categoryUseCase.getAllCategory();

        // Verificaciones
        assertNotNull(actualCategories);
        assertEquals(2, actualCategories.size());
        assertEquals("Books", actualCategories.get(0).getName());

        // Verificar que el método getAllCategory fue llamado una vez en el puerto de persistencia
        verify(categoryPersistencePort, times(1)).getAllCategory();
    }

    @Test
    void testGetCategory() {
        Long categoryId = 1L;
        Category expectedCategory = new Category(1L, "Hogar", "Productos y accesorios para organizar, decorar y equipar cada rincón de tu hogar.");

        // Mockear el comportamiento del puerto de persistencia
        when(categoryPersistencePort.getCategory(categoryId)).thenReturn(expectedCategory);

        // Ejecutar el método de la lógica de negocio
        Category actualCategory = categoryUseCase.getCategory(categoryId);

        // Verificaciones
        assertNotNull(actualCategory);
        assertEquals(expectedCategory.getName(), actualCategory.getName());
        assertEquals(expectedCategory.getDescription(), actualCategory.getDescription());
        assertEquals(expectedCategory.getId(), actualCategory.getId());

        // Verificar que el método getCategory fue llamado una vez en el puerto de persistencia
        verify(categoryPersistencePort, times(1)).getCategory(categoryId);
    }
}
