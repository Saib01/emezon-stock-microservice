package com.emazon.stock.dominio.api;

import com.emazon.stock.dominio.modelo.Brand;

public interface IBrandServicePort {
    void saveBrand(Brand brand);
    Brand getBrand(Long id);
}
