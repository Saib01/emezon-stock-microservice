package com.emazon.stock.aplicacion.mapper;


import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import org.mapstruct.Mapper;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "Spring")
public interface CategoryResponseMapper {
    CategoryResponse toCategoryResponse( Category category);

    List<CategoryResponse> toCategoryResponsesList(List<Category> categories) ;
    default  Page<CategoryResponse> toCategoryResponsePage(PageStock<Category> categories, Pageable pageable) {
        List<CategoryResponse> categoryResponseList=toCategoryResponsesList(categories.getContent());
        return new PageImpl<>(
                Objects.requireNonNullElse(categoryResponseList, Collections.emptyList()),
                pageable,
                categories.getTotalElements()
        );
    }

}
