package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.BrandResponse;
import com.emazon.stock.dominio.modelo.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface BrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);
}
