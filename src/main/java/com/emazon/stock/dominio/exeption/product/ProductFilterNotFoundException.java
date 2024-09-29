package com.emazon.stock.dominio.exeption.product;

public class ProductFilterNotFoundException extends RuntimeException {
    public ProductFilterNotFoundException(String filterNotFound) {
        super(filterNotFound);
    }
}