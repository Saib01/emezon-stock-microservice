package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {
    Brand toBrand(BrandEntity brand);

    PageStock<Brand> toPageStock(Page<BrandEntity> brandEntityPage);
    BrandEntity toBrandEntity(Brand brand);
}
