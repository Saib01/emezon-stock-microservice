package com.emazon.stock.dominio.usecase;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    void testGetCategoryPage() {
        // Datos de ejemplo
        Category category1 = new Category(1L, "Electronics", "Gadgets and devices");
        Category category2 = new Category(2L, "Books", "Books and literature");

        // Lista de categorías esperadas
        List<Category> categoryList = Arrays.asList(category1, category2);

        // Pageable para el request
        Pageable pageable = PageRequest.of(0, 10);

        // Crear una página esperada de categorías
        Page<Category> expectedCategories = new PageImpl<>(categoryList, pageable, categoryList.size());

        // Aquí iría la llamada al método que estás probando
        // Por ejemplo, supongamos que estás llamando a getCategories del servicio
        Page<Category> actualCategories = categoryPersistencePort.getCategories("asc", 0, 10);

        // Comparar resultados esperados con los actuales
        assertEquals(expectedCategories, actualCategories);
        // Verificar que el método getAllCategory fue llamado una vez en el puerto de persistencia
        verify(categoryPersistencePort, times(1)).getCategories("asc", 0, 10);
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
