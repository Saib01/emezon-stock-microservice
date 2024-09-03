package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.ProductResponse;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.infraestructura.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.mapper.CategoryEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = {BrandEntityMapper.class, CategoryEntityMapper.class})
public interface ProductResponseMapper {
    @Mapping(source = "brand", target = "brandResponse")
    @Mapping(source = "categoryList", target = "categoryResponseList")
    ProductResponse toProductResponse(Product product);


    List<ProductResponse> toProductResponsesList(List<Product> products) ;
    @Mapping(target = "brandResponse.description",ignore = true)
    @Mapping(target = "categoryResponse.description",ignore = true)
    default Page<ProductResponse> toProductResponsePage(PageStock<Product> products,Pageable pageable ){
        List<ProductResponse> productResponseList=toProductResponsesList(products.getContent());
        return new PageImpl<>(
                Objects.requireNonNullElse(productResponseList, Collections.emptyList()),
                pageable,
                products.getTotalElements()
        );
    }
}
