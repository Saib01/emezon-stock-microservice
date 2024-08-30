package com.emazon.stock.dominio.spi;

import com.emazon.stock.dominio.modelo.Brand;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    Brand getBrand(Long id);
    boolean findByName(String name);
}
