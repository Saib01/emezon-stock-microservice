package com.emazon.stock.infraestructura.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.emazon.stock.dominio.utils.DomainConstants.BRAND;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.*;


@Entity
@Table(name = BRAND)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_BRAND, nullable = false)
    private Long id;
    @Column(nullable = false, columnDefinition = MAX_CHAR_BRAND_NAME, unique = true)
    private String name;
    @Column(nullable = false, columnDefinition = MAX_CHAR_BRAND_DESCRIPTION)
    private String description;
    @OneToMany(mappedBy = BRAND_ENTITY,fetch = FetchType.EAGER)
    private List<ProductEntity> productEntities;

    public BrandEntity(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
