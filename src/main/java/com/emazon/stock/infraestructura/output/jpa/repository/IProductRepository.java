package com.emazon.stock.infraestructura.output.jpa.repository;

import com.emazon.stock.infraestructura.output.jpa.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);
    Optional<ProductEntity> findById(Long id);

    @Query("SELECT p FROM ProductEntity p " +
            "JOIN p.categoryEntityList c " +
            "JOIN p.brandEntity b " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'category' THEN c.name " +
            "WHEN :sortBy = 'brand' THEN b.name " +
            "ELSE p.name END")

    Page<ProductEntity> getProductsBySearchTerm(@Param("sortBy") String sortBy, Pageable pageable);
}
