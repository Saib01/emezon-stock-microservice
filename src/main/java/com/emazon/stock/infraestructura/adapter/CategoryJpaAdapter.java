package com.emazon.stock.infraestructura.adapter;

import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.infraestructura.exceptions.*;
import com.emazon.stock.infraestructura.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.ICategoryRepository;
import com.emazon.stock.infraestructura.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;

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
    public List<Category> getAllCategory() {
        return this.categoryRepository.findAll().stream()
                .map(categoryEntityMapper::toCategory)
                .toList();
    }

    @Override
    public Category getCategory(Long id) {
        return categoryEntityMapper.toCategory(categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("No se encontro la categoria")));
    }
}