package com.emazon.stock.aplicacion.handler.implement;

import com.emazon.stock.aplicacion.dtos.request.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.response.CategoryResponse;
import com.emazon.stock.aplicacion.handler.ICategoryHandler;
import com.emazon.stock.aplicacion.mapper.CategoryRequestMapper;
import com.emazon.stock.aplicacion.mapper.CategoryResponseMapper;
import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
        public PageStock<CategoryResponse> getCategoriesByName(int page, int size, String sortDirection) {
                return this.categoryResponseMapper.toCategoryResponsePageStock(
                        categoryServicePort.getCategoriesByName(page,size,sortDirection)
                );
        }

        @Override
        public CategoryResponse getCategory(Long categoryNumber) {
                return categoryResponseMapper.toCategoryResponse(categoryServicePort.getCategory(categoryNumber));
        }
}
