package com.emazon.stock.aplicacion.mapper.brand;

import com.emazon.stock.aplicacion.dtos.brand.BrandResponse;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.utils.PageStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static com.emazon.stock.aplicacion.util.ApplicationConstants.DESCRIPTION;
import static com.emazon.stock.aplicacion.util.ApplicationConstants.MAP_BRAND_WITHOUT_DESCRIPTION;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BrandResponseMapper {

    BrandResponse toBrandResponse(Brand brand);

    List<BrandResponse> toBrandResponsesList(List<Brand> brands);

    PageStock<BrandResponse> toBrandResponsePageStock(PageStock<Brand> brandPageStock);

    @Named(MAP_BRAND_WITHOUT_DESCRIPTION)
    @Mapping(target = DESCRIPTION, ignore = true)
    BrandResponse mapBrandWithoutDescription(Brand brand);
}
