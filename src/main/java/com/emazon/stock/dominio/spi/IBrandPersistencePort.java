package com.emazon.stock.dominio.spi;

import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.PageStock;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    PageStock<Brand> getBrandsByName(int page, int size, String sortDirection);
    Brand getBrand(Long id);
    boolean findByName(String name);
}
