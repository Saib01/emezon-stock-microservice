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

import java.util.List;
import java.util.Objects;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;
import static com.emazon.stock.dominio.utils.DomainConstants.ZERO;
import static java.math.BigInteger.ONE;


@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {
    public static final String ORDER_BY_CATEGORY = "categoryEntityList.name";
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product) {
        this.productRepository.save(productEntityMapper.toProductEntity(product));
    }

    @Override
    public PageStock<Product> getProductsBySearchTerm(int page, int size, List<String> sortBy, String sortDirection) {
        Pageable pageable =
                sortBy.get(ZERO).equalsIgnoreCase(ORDER_BY_CATEGORY) ?
                        PageRequest.of(page, size)
                        : PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy.get(ZERO), sortBy.get(ONE.intValue())));

        Page<ProductEntity> productEntities =
                sortBy.get(ZERO).equalsIgnoreCase(ORDER_BY_CATEGORY) ?
                        this.getProductEntitiesOrderByCategoryName(sortDirection, pageable)
                        : productRepository.findAll(pageable);

        return productEntityMapper.toProductPageStock(
                productEntities
        );
    }

    private Page<ProductEntity> getProductEntitiesOrderByCategoryName(String sortDirection, Pageable pageable) {
        return sortDirection.equalsIgnoreCase(ASC) ?
                productRepository.findAllOrderByCategoryNameAndProductNameAndBrandNameASC(pageable)
                : productRepository.findAllOrderByCategoryNameAndProductNameAndBrandNameDESC(pageable);
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

    @Override
    public PageStock<Product> getPaginatedProductsInShoppingCart(List<Long> listIdsProducts, List<String> filter, int page, int size, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), PROPERTY_NAME.toLowerCase()));
        Page<ProductEntity> productEntities;
        if (filter.stream().allMatch(Objects::nonNull)) {
            productEntities = this.productRepository.findByIdInAndCategoryEntityList_NameAndBrandEntity_Name(listIdsProducts, filter.get(ZERO), filter.get(ONE.intValue()), pageable);
        } else {
            productEntities = filter.stream().allMatch(Objects::isNull) ?
                    this.productRepository.findByIdIn(listIdsProducts, pageable)
                    : getProductEntitiesByCategoryNameOrBrandName(listIdsProducts, filter.get(ZERO), filter.get(ONE.intValue()), pageable);
        }
        return productEntityMapper.toProductPageStock(
                productEntities
        );
    }

    @Override
    public List<Product> getProductsByProductIds(List<Long> listIdsProducts) {
        return productRepository.findByIdIn(listIdsProducts)
                .stream()
                .map(productEntityMapper::toProduct)
                .toList();
    }

    @Override
    public void saveAllProduct(List<Product> products) {
        this.productRepository.saveAll(
                products.stream().map(this.productEntityMapper::toProductEntity).toList()
        );
    }

    private Page<ProductEntity> getProductEntitiesByCategoryNameOrBrandName(List<Long> listIdsProducts, String categoryName, String brandName, Pageable pageable) {
        return categoryName != null ?
                this.productRepository.findByIdInAndCategoryEntityList_Name(listIdsProducts, categoryName, pageable)
                : this.productRepository.findByIdInAndBrandEntity_Name(listIdsProducts, brandName, pageable);
    }

}