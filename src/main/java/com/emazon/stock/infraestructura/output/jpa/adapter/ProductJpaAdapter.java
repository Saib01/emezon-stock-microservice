package com.emazon.stock.infraestructura.output.jpa.adapter;


import com.emazon.stock.dominio.exeption.product.ProductNotFoundException;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.spi.IProductPersistencePort;
import com.emazon.stock.infraestructura.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;
    @Override
    public void saveProduct(Product product) {
        this.productRepository.save(productEntityMapper.toProductEntity(product));
    }
    
    @Override
    public Product getProduct(Long id) {
        return productEntityMapper.toProduct(
                this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new));
    }

    @Override
    public boolean existsByName(String name) {
        return this.productRepository.existsByName(name);
    }

    @Override
    public boolean existsById(Long id) {
        return this.productRepository.existsById(id);
    }
}