package com.emazon.stock.infraestructura.output.jpa.adapter;


import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.spi.IProductPersistencePort;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import com.emazon.stock.infraestructura.output.jpa.entity.ProductEntity;
import com.emazon.stock.infraestructura.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.util.List;


@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product) {
        this.productRepository.save(productEntityMapper.toProductEntity(product));
    }

    @Override
    public PageStock<Product> getProductsBySearchTerm(int page, int size, List<String> sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size
                //,Sort.by(Sort.Direction.fromString("ASC"), "categoryEntityList.size()")//"categoryEntityList.name"
                       // .and(Sort.by(Sort.Direction.fromString(sortDirection), sortBy.get(ONE.intValue())))
                       // .and(Sort.by(Sort.Direction.fromString(sortDirection), sortBy.get(TWO.intValue())))
        );
        //Page<ProductEntity> productEntities=this.productRepository.getProductsBySearchTerm("category",pageable);

        Page<ProductEntity> productEntities=this.productRepository.getProductsBySearchTerm("category",pageable);
        return productEntityMapper.toProductPageStock(
                productEntities
        );
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .map(productEntityMapper::toProduct)
                .orElse(null);
    }

    @Override
    public boolean existsByName(String name) {
        return this.productRepository.existsByName(name);
    }

    @Override
    public boolean existsById(Long id) {
        return this.productRepository.existsById(id);
    }

    @Override
    public List<List<Long>> getCategoryIdsByProductIds(List<Long> listIdsProducts) {
        return productRepository.findByIdIn(listIdsProducts).stream()
                .map(product -> product.getCategoryEntityList().stream()
                        .map(CategoryEntity::getId)
                        .toList())
                .toList();
    }
}