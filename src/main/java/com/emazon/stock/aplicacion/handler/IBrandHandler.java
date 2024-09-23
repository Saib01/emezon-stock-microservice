package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.request.BrandRequest;
import com.emazon.stock.aplicacion.dtos.response.BrandResponse;
import com.emazon.stock.dominio.modelo.PageStock;

public interface IBrandHandler {
    void saveBrand(BrandRequest brandRequest);
    PageStock<BrandResponse> getBrandsByName(int page, int size, String sortDirection);
    BrandResponse getBrand(Long id);
}
