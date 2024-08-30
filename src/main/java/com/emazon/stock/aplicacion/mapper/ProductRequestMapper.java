package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.ProductRequest;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ProductRequestMapper {
    default Product toProduct(ProductRequest productRequest){
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
        return categoryIds.stream()
                .map(this::createCategory)
                .toList();
    }
    default Category createCategory(Long categoryId) {
        return new Category(categoryId, null, null);
    }
}
