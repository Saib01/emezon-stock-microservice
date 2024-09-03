package com.emazon.stock.infraestructura.output.jpa.mapper;



import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.infraestructura.output.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {BrandEntityMapper.class, CategoryEntityMapper.class})
public interface ProductEntityMapper {
    @Mapping(source = "brandEntity",target = "brand")
    @Mapping(source = "categoryEntityList",target = "categoryList")
    Product toProduct(ProductEntity productEntity);

    @Mapping(source = "brand",target = "brandEntity")
    @Mapping(source = "categoryList",target = "categoryEntityList")
    ProductEntity toProductEntity(Product product);

    PageStock<Product> toProductPageStock(Page<ProductEntity> productEntityPage);

}
