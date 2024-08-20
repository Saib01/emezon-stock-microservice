package com.emazon.stock.infraestructura.adapter;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.infraestructura.exceptions.*;
import com.emazon.stock.infraestructura.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.ICategoryRepository;
import com.emazon.stock.infraestructura.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {



    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private static final  Long MAX_DESCRIPTION_LENGTH =90L;
    private static final  Long MAX_NAME_LENGTH =50L;


    @Override
    public void saveCategory(Category category) {
        if(categoryRepository.existsByName(category.getName())){
            throw new CategoryAlreadyExistException("Ya existe una categoria con ese nombre.");
        }
        if(category.getName().length()> MAX_NAME_LENGTH){
            throw new CategoryNameTooLongException("El nombre tiene un maximo de caracteres permitido de 50");
        }
        if(category.getDescription().isEmpty()){
            throw new CategoryDescriptionRequiredException("La categoria no presenta una descripcion");
        }
        if(category.getDescription().length()> MAX_DESCRIPTION_LENGTH){
            throw new CategoryDescriptionTooLongException("La descripcion tiene un maximo de caracteres permitido de 90");
        }
        this.categoryRepository.save(
                categoryEntityMapper.toCategoryEntity(category)
        );
    }

    @Override
    public Page<Category> getCategories(String sortDirection, int page, int size) {
        if(sortDirection.equals("ASC")||sortDirection.equals("DESC")){
            throw new CategorySortDirectionException("La direccion de ordenamiento solo puede ser ASC o DESC");
        }
        // Determinar la dirección del ordenamiento
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        // Crear un objeto Pageable con la dirección de ordenamiento, página y tamaño
        Pageable pageable = PageRequest.of(page, size, direction, "name"); // Cambia "name" por el campo por el que quieras ordenar
        return categoryEntityMapper.toCategoryPage(this.categoryRepository.findAll(pageable));
    }

    @Override
    public Category getCategory(Long id) {
        return categoryEntityMapper.toCategory(categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("No se encontro la categoria")));
    }
}