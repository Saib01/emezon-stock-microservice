package com.emazon.stock.aplicacion.mapper;

import com.emazon.stock.aplicacion.dtos.BrandResponse;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.PageStock;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "Spring")
public interface BrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);
    List<BrandResponse> toBrandResponsesList(List<Brand> brands) ;
    default Page<BrandResponse> toBrandResponsePage(PageStock<Brand> brands, Pageable pageable) {
        List<BrandResponse> brandResponseList= toBrandResponsesList(brands.getContent());
        return new PageImpl<>(
                Objects.requireNonNullElse(brandResponseList, Collections.emptyList()),
                pageable,
                brands.getTotalElements()
        );
    }
}
