package com.emazon.stock.aplicacion.mapper.brand;


import com.emazon.stock.aplicacion.dtos.brand.BrandRequest;
import com.emazon.stock.dominio.modelo.Brand;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BrandRequestMapper {
    Brand toBrand(BrandRequest marcaRequest);
}
