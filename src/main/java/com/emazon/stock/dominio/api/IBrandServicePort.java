package com.emazon.stock.dominio.api;

import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.utils.PageStock;

public interface IBrandServicePort {
    void saveBrand(Brand brand);

    PageStock<Brand> getBrandsByName(int page, int size, String sortDirection);

    Brand getBrand(Long id);

    Boolean isBrandNameAvailable(String brandName);
}
