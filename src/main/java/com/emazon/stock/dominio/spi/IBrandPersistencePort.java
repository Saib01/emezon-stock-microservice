package com.emazon.stock.dominio.spi;

import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.utils.PageStock;

public interface IBrandPersistencePort extends IModelPersistencePort {
    void saveBrand(Brand brand);

    PageStock<Brand> getBrandsByName(int page, int size, String sortDirection);

    Brand getBrand(Long id);
}
