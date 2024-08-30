package com.emazon.stock.infraestructura.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_category",nullable = false)
    private Long id;
    @Column(nullable = false,columnDefinition = "CHAR(50)",unique = true)
    private String name;
    @Column(nullable = false,columnDefinition = "CHAR(90)")
    private String description;
}
