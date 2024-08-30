package com.emazon.stock.infraestructura.output.jpa.repository;

import com.emazon.stock.infraestructura.output.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    boolean existsByName(String name);

    Optional<BrandEntity> findById(Long id);
}
