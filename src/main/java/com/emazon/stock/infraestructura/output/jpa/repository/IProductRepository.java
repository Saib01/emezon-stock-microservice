package com.emazon.stock.infraestructura.output.jpa.repository;

import com.emazon.stock.infraestructura.output.jpa.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);
    Optional<ProductEntity> findById(Long id);
    Page<ProductEntity> findAll(Pageable pageable);
    List<ProductEntity> findByIdIn(List<Long> ids);
}