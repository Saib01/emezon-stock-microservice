package com.emazon.stock.infraestructura.output.jpa.repository;

import com.emazon.stock.infraestructura.output.jpa.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);

    Optional<ProductEntity> findById(Long id);
    Page<ProductEntity> findAll(Pageable pageable);
/*
    @Query("SELECT p FROM ProductEntity p " +
            "JOIN p.categoryEntityList c " +
            "JOIN p.brandEntity b " +
            "GROUP BY p.productId" +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'category' THEN c.name " +
            "WHEN :sortBy = 'brand' THEN b.name " +
            "ELSE p.name END")*/
@Query("SELECT p FROM ProductEntity p LEFT JOIN p.categoryEntityList c " + "GROUP BY p.product_Id " + "ORDER BY COUNT(c.categoryId) ASC, MIN(c.categoryName) ASC")

Page<ProductEntity> getProductsBySearchTerm(@Param("sortBy") String sortBy, Pageable pageable);
    List<ProductEntity> findByIdIn(List<Long> ids);
}