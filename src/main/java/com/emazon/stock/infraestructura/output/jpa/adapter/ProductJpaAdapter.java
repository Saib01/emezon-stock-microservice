package com.emazon.stock.infraestructura.output.jpa.adapter;


import com.emazon.stock.dominio.exeption.product.ProductNotFoundException;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.modelo.Product;
import com.emazon.stock.dominio.spi.IProductPersistencePort;
import com.emazon.stock.infraestructura.output.jpa.entity.CategoryEntity;
import com.emazon.stock.infraestructura.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;

@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    private static final Long INCREMENT =1L;
    @Override
    public void saveProduct(Product product) {
        this.productRepository.save(productEntityMapper.toProductEntity(product));
    }

    @Override
    public PageStock<Product> getProductsBySearchTerm(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDirection),PROPERTY_NAME.toLowerCase()));//
        return  productEntityMapper.toProductPageStock(
                this.productRepository.getProductsBySearchTerm(sortBy,pageable));
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

    @Override
    public List<List<Long>> getCategoryIdsByProductIds(List<Long> listIdsProducts) {
        return productRepository.findByIdIn(listIdsProducts).stream()
                .map(product -> product.getCategoryEntityList().stream()
                        .map(CategoryEntity::getId)
                        .toList())
                .toList();
    }
}