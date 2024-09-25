package com.emazon.stock.infraestructura.output.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.emazon.stock.dominio.utils.DomainConstants.CATEGORY;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.*;

@Entity
@Table(name = CATEGORY)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_CATEGORY, nullable = false)
    private Long id;
    @Column(nullable = false, columnDefinition = MAX_CHAR_CATEGORY_NAME, unique = true)
    private String name;
    @Column(nullable = false, columnDefinition = MAX_CHAR_CATEGORY_DESCRIPTION)
    private String description;
    @ManyToMany(mappedBy = CATEGORY_ENTITY_LIST, fetch = FetchType.LAZY)
    private List<ProductEntity> productEntities;

    public CategoryEntity(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
