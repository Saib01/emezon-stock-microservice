package com.emazon.stock.aplicacion.mapper.product;


import com.emazon.stock.aplicacion.dtos.product.ProductResponse;
import com.emazon.stock.aplicacion.mapper.brand.BrandResponseMapper;
import com.emazon.stock.aplicacion.mapper.category.CategoryResponseMapper;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.utils.PageStock;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static com.emazon.stock.aplicacion.util.ApplicationConstants.*;
import static com.emazon.stock.dominio.utils.DomainConstants.BRAND;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {BrandResponseMapper.class, CategoryResponseMapper.class})
public interface ProductResponseMapper {

    @Mapping(source = BRAND, target = BRAND_RESPONSE)
    @Mapping(source = CATEGORY_LIST, target = CATEGORY_RESPONSE_LIST)
    ProductResponse toProductResponse(Product product);

    @Named(MAP_PRODUCT_WITHOUT_CATEGORY_AND_BRAND_DESCRIPTIONS)
    @Mapping(target = CATEGORY_RESPONSE_LIST, source = CATEGORY_LIST, qualifiedByName = MAP_CATEGORY_LIST_WITHOUT_DESCRIPTIONS)
    @Mapping(target = BRAND_RESPONSE, source = BRAND, qualifiedByName = MAP_BRAND_WITHOUT_DESCRIPTION)
    ProductResponse mapProductWithoutCategoryAndBrandDescriptions(Product product);

    @IterableMapping(qualifiedByName = MAP_PRODUCT_WITHOUT_CATEGORY_AND_BRAND_DESCRIPTIONS)
    List<ProductResponse> mapProductListWithoutCategoryAndBrandDescriptions(List<Product> products);

    @Mapping(target = CONTENT, expression = JAVA_MAP_PRODUCT_LIST_WITHOUT_CATEGORY_DESCRIPTIONS_PRODUCT_PAGE_STOCK_GET_CONTENT)
    PageStock<ProductResponse> toProductResponsePageStock(PageStock<Product> productPageStock);

}
