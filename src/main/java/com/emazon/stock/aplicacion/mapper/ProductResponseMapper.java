package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.ProductResponse;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.infraestructura.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.mapper.CategoryEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BrandEntityMapper.class, CategoryEntityMapper.class})
public interface ProductResponseMapper {
    @Mapping(source = "brand",target = "brandResponse")
    @Mapping(source = "categoryList",target = "categoryResponseList")
    ProductResponse toProductResponse(Product product);
}
