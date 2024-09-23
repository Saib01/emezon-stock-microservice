package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.response.BrandResponse;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.PageStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface BrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);
    List<BrandResponse> toBrandResponsesList(List<Brand> brands) ;
    PageStock<BrandResponse> toBrandResponsePageStock(PageStock<Brand> brandPageStock);


    @Named("mapWithoutBrandDescription")
    @Mapping(target = "description", ignore = true)
    BrandResponse mapWithoutBrandDescription(Brand brand);
}
