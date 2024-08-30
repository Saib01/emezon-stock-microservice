package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.BrandRequest;
import com.emazon.stock.aplicacion.dtos.BrandResponse;

public interface IBrandHandler {
    void saveBrand(BrandRequest brandRequest);
    BrandResponse getBrand(Long id);
}
