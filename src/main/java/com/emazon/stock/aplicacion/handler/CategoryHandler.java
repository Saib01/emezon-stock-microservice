package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.aplicacion.mapper.CategoryRequestMapper;
import com.emazon.stock.aplicacion.mapper.CategoryResponseMapper;
import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

        private final ICategoryServicePort categoryServicePort;
        private final CategoryResponseMapper categoryResponseMapper;
        private final CategoryRequestMapper categoryRequestMapper;

        @Override
        public void saveCategory(CategoryRequest categoryRequest) {
                Category category = categoryRequestMapper.toCategory(categoryRequest);
                categoryServicePort.saveCategory(category);
        }

        @Override
        public List<CategoryResponse> getAllCategory() {
                return categoryResponseMapper.toCategoryResponseList(categoryServicePort.getAllCategory());
        }

        @Override
        public CategoryResponse getCategory(Long categoryNumber) {
                return categoryResponseMapper.toCategoryResponse(categoryServicePort.getCategory(categoryNumber));
        }
}
