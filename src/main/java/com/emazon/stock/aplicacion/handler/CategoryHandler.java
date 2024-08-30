package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.aplicacion.mapper.CategoryRequestMapper;
import com.emazon.stock.aplicacion.mapper.CategoryResponseMapper;
import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.modelo.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.emazon.stock.dominio.utils.ConstantsDominio.PROPERTY_NAME;

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
        public Page<CategoryResponse> getCategoriesByName(int page,int size,String sortDirection) {
                return this.categoryResponseMapper.toCategoryResponsePage(
                        categoryServicePort.getCategoriesByName(page,size,sortDirection),
                        PageRequest.of(
                                page,
                                size,
                                Sort.by(Sort.Direction.fromString(sortDirection),PROPERTY_NAME.toLowerCase())
                        )
                );
        }

        @Override
        public CategoryResponse getCategory(Long categoryNumber) {
                return categoryResponseMapper.toCategoryResponse(categoryServicePort.getCategory(categoryNumber));
        }
}
