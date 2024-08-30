package com.emazon.stock.infraestructura.output.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
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
    @Column(nullable = false,columnDefinition = "CHAR(90)")
    private String description;

}
