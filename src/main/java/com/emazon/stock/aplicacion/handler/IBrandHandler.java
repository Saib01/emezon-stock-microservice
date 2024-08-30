package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.BrandRequest;
import com.emazon.stock.aplicacion.dtos.BrandResponse;
import org.springframework.data.domain.Page;

public interface IBrandHandler {
    void saveBrand(BrandRequest brandRequest);
    Page<BrandResponse> getBrandsByName(int page, int size, String sortDirection);
    BrandResponse getBrand(Long id);
}
