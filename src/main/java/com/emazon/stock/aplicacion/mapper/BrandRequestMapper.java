package com.emazon.stock.aplicacion.mapper;


import com.emazon.stock.aplicacion.dtos.BrandRequest;
import com.emazon.stock.dominio.modelo.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandRequestMapper {
    Brand toBrand(BrandRequest marcaRequest);
}
