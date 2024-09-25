package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.brand.BrandRequest;
import com.emazon.stock.aplicacion.dtos.brand.BrandResponse;
import com.emazon.stock.dominio.utils.PageStock;

public interface IBrandHandler {
    void saveBrand(BrandRequest brandRequest);

    PageStock<BrandResponse> getBrandsByName(int page, int size, String sortDirection);

    BrandResponse getBrand(Long id);
}
