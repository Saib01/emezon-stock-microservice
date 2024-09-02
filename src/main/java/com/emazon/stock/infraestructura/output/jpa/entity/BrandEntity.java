package com.emazon.stock.infraestructura.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
}
