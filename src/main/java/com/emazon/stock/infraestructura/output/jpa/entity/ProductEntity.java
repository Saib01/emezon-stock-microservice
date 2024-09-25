package com.emazon.stock.infraestructura.output.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.emazon.stock.dominio.utils.DomainConstants.PRODUCT;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.*;

@Entity
@Table(name = PRODUCT)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_PRODUCT, nullable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private Double price;
    @ManyToOne
    @JoinColumn(name = BRAND_ID)
    private BrandEntity brandEntity;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(
            name = PRODUCT_CATEGORY,
            joinColumns = @JoinColumn(name = PRODUCT_ID,referencedColumnName = ID_PRODUCT),
            inverseJoinColumns = @JoinColumn(name = CATEGORY_ID,referencedColumnName = ID_CATEGORY)
    )
    private List<CategoryEntity> categoryEntityList;

}
