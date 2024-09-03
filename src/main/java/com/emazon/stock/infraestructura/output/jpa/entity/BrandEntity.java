package com.emazon.stock.infraestructura.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name="brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_brand",nullable = false)
    private Long id;
    @Column(nullable = false,columnDefinition = "CHAR(50)",unique = true)
    private String name;
    @Column(nullable = false,columnDefinition = "CHAR(120)")
    private String description;
    @OneToMany(mappedBy = "brandEntity",fetch = FetchType.LAZY)
    private List<ProductEntity> productEntities;

    public BrandEntity(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
