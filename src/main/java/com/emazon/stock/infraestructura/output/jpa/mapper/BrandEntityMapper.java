package com.emazon.stock.infraestructura.output.jpa.mapper;

import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.emazon.stock.aplicacion.util.applicationConstants.CONTENT;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.*;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BrandEntityMapper {


    Brand toBrand(BrandEntity brand);

    @Mapping(target = PRODUCT_ENTITIES, ignore = true)
    BrandEntity toBrandEntity(Brand brand);

    @Mapping(target = CONTENT, expression = EMPTY_IF_NULL_BRAND_ENTITY_PAGE_GET_CONTENT)
    @Mapping(target = PAGE_NUMBER, source = NUMBER)
    @Mapping(target = PAGE_SIZE, source = SIZE)
    @Mapping(target = ASCENDING, ignore = true)
    PageStock<Brand> toBrandPageStock(Page<BrandEntity> brandEntityPage);

    default List<Brand> mapContentToEmptyIfNull(List<BrandEntity> content) {
        return Optional.ofNullable(content.stream()
                        .map(this::toBrand)
                        .toList())
                .orElse(Collections.emptyList());
    }
}
