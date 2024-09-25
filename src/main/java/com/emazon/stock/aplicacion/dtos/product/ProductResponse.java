package com.emazon.stock.aplicacion.dtos.product;

import com.emazon.stock.aplicacion.dtos.brand.BrandResponse;
import com.emazon.stock.aplicacion.dtos.category.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Integer amount;
    private Double price;
    private BrandResponse brandResponse;
    private List<CategoryResponse> categoryResponseList;
}