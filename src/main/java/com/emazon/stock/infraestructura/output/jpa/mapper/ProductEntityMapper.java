package com.emazon.stock.infraestructura.output.jpa.mapper;


import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.emazon.stock.aplicacion.util.applicationConstants.CATEGORY_LIST;
import static com.emazon.stock.aplicacion.util.applicationConstants.CONTENT;
import static com.emazon.stock.dominio.utils.DomainConstants.BRAND;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.*;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {BrandEntityMapper.class, CategoryEntityMapper.class})
public interface ProductEntityMapper {

    @Mapping(source = BRAND_ENTITY, target = BRAND)
    @Mapping(source = CATEGORY_ENTITY_LIST, target = CATEGORY_LIST)
    Product toProduct(ProductEntity productEntity);

    @Mapping(source = BRAND, target = BRAND_ENTITY)
    @Mapping(source = CATEGORY_LIST, target = CATEGORY_ENTITY_LIST)
    ProductEntity toProductEntity(Product product);

    @Mapping(target = CONTENT, expression = EMPTY_IF_NULL_PRODUCT_ENTITY_PAGE_GET_CONTENT)
    @Mapping(target = PAGE_NUMBER, source = NUMBER)
    @Mapping(target = PAGE_SIZE, source = SIZE)
    @Mapping(target = ASCENDING, ignore = true)
    PageStock<Product> toProductPageStock(Page<ProductEntity> productEntityPage);

    default List<Product> mapContentToEmptyIfNull(List<ProductEntity> content) {
        return Optional.ofNullable(content.stream()
                        .map(this::toProduct)
                        .toList())
                .orElse(Collections.emptyList());
    }
}
