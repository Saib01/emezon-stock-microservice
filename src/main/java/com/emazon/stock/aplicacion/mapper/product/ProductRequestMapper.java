package com.emazon.stock.aplicacion.mapper.product;

import com.emazon.stock.aplicacion.dtos.product.ProductRequest;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.Product;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProductRequestMapper {
    default Product toProduct(ProductRequest productRequest) {
        return new Product(
                productRequest.getId(),
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getAmount(),
                productRequest.getPrice(),
                createBrand(productRequest.getBrandId()),
                createCategories(productRequest.getCategoryIdsList())
        );
    }

    default Brand createBrand(Long brandId) {
        return new Brand(brandId, null, null);
    }

    default List<Category> createCategories(List<Long> categoryIds) {
        return Optional.ofNullable(categoryIds)
                .map(ids -> ids.stream()
                        .map(this::createCategory)
                        .toList())
                .orElse(Collections.emptyList());
    }

    default Category createCategory(Long categoryId) {
        return new Category(categoryId, null, null);
    }
}
