package com.emazon.stock.aplicacion.mapper;


import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Mapper(componentModel = "Spring")
public interface CategoryResponseMapper {
    CategoryResponse toCategoryResponse( Category category);
    default  Page<CategoryResponse> toCategoryResponsePage(PageStock<Category> categoryPageStock) {

        Pageable pageable = PageRequest.of(
                categoryPageStock.getPageNumber(),
                categoryPageStock.getPageSize(),
                Sort.by(Sort.Direction.fromString(categoryPageStock.getSortDirection()), categoryPageStock.getSortBy())
        );
        return new PageImpl<>(
                categoryPageStock.getContent().stream()
                        .map(category -> {
                            CategoryResponse response = new CategoryResponse();
                            response.setId(category.getId());
                            response.setName(category.getName());
                            response.setDescription(category.getDescription());
                            return response;
                        })
                        .toList(),
                pageable,
                categoryPageStock.getTotalElements()
        );
    }
}
