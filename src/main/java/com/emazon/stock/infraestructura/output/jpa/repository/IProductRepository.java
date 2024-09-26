package com.emazon.stock.infraestructura.output.jpa.repository;

import com.emazon.stock.infraestructura.output.jpa.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);

    Optional<ProductEntity> findById(Long id);
    Page<ProductEntity> findAll(Pageable pageable);

    List<ProductEntity> findByIdIn(List<Long> ids);

    @Query("SELECT p FROM ProductEntity p " +
            " JOIN p.categoryEntityList c " +
            " JOIN p.brandEntity b " +
            "GROUP BY p " +
            "ORDER BY MIN(c.name) ASC"
            +", p.name ASC, b.name ASC"
    )
    Page<ProductEntity> findAllOrderByCategoryNameAndProductNameAndBrandNameASC(Pageable pageable);
    @Query("SELECT p FROM ProductEntity p " +
            " JOIN p.categoryEntityList c " +
            " JOIN p.brandEntity b " +
            "GROUP BY p " +
            "ORDER BY MIN(c.name) DESC"
            +", p.name DESC, b.name DESC"
            )
    Page<ProductEntity> findAllOrderByCategoryNameAndProductNameAndBrandNameDESC(Pageable pageable);

    Page<ProductEntity> findByIdInAndCategoryEntityList_NameAndBrandEntity_Name(List<Long> ids, String categoryName, String brandName, Pageable pageable);
    Page<ProductEntity> findByIdInAndCategoryEntityList_Name(List<Long> ids, String categoryName, Pageable pageable);
    Page<ProductEntity> findByIdInAndBrandEntity_Name(List<Long> ids, String brandName, Pageable pageable);


}