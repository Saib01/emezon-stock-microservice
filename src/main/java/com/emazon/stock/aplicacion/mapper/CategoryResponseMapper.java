package com.emazon.stock.aplicacion.mapper;


import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface CategoryResponseMapper {
    CategoryResponse toCategoryResponse( Category category);
    default  Page<CategoryResponse> toCategoryResponsePage(Page<Category> categoryPage) {
        return new PageImpl<>(
                categoryPage.stream()
                        .map(category -> {
                            CategoryResponse response = new CategoryResponse();
                            response.setId(category.getId());
                            response.setName(category.getName());
                            response.setDescription(category.getDescription());
                            return response;
                        })
                        .toList(),
                categoryPage.getPageable(),
                categoryPage.getTotalElements()
        );
    }
}
