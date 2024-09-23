package com.emazon.stock.aplicacion.mapper;


import com.emazon.stock.aplicacion.dtos.response.ProductResponse;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BrandResponseMapper.class, CategoryResponseMapper.class})
public interface ProductResponseMapper {
    @Mapping(source = "brand", target = "brandResponse")
    @Mapping(source = "categoryList", target = "categoryResponseList")
    ProductResponse toProductResponse(Product product);

    @Named("mapWithoutCategoryDescriptionsInProduct")
    @Mapping(target = "categoryResponseList", source = "categoryList", qualifiedByName = "mapWithoutCategoryDescription")
    @Mapping(target = "brandResponse",source = "brand", qualifiedByName = "mapWithoutBrandDescription")
    ProductResponse mapWithoutCategoryDescriptionsInProduct(Product product);

    @IterableMapping(qualifiedByName = "mapWithoutCategoryDescriptionsInProduct")
    List<ProductResponse> mapProductListWithoutCategoryDescriptions(List<Product> products);

    @Mapping(target = "content", expression = "java(mapProductListWithoutCategoryDescriptions(productPageStock.getContent()))")
    PageStock<ProductResponse> toProductResponsePageStock(PageStock<Product> productPageStock);

}
