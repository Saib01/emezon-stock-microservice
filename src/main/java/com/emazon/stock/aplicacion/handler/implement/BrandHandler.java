package com.emazon.stock.aplicacion.handler.implement;

import com.emazon.stock.aplicacion.dtos.request.BrandRequest;
import com.emazon.stock.aplicacion.dtos.response.BrandResponse;
import com.emazon.stock.aplicacion.handler.IBrandHandler;
import com.emazon.stock.aplicacion.mapper.BrandRequestMapper;
import com.emazon.stock.aplicacion.mapper.BrandResponseMapper;
import com.emazon.stock.dominio.api.IBrandServicePort;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.PageStock;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler {
    private final IBrandServicePort brandServicePort;
    private final BrandResponseMapper brandResponseMapper;
    private final BrandRequestMapper brandRequestMapper;

    @Override
    public void saveBrand(BrandRequest brandRequest) {
        Brand brand=brandRequestMapper.toBrand(brandRequest);
        brandServicePort.saveBrand(brand);
    }

    @Override
    public PageStock<BrandResponse> getBrandsByName(int page, int size, String sortDirection) {
        return this.brandResponseMapper.toBrandResponsePageStock(
                brandServicePort.getBrandsByName(page,size,sortDirection)
        );
    }

    @Override
    public BrandResponse getBrand(Long id) {
        return brandResponseMapper.toBrandResponse(brandServicePort.getBrand(id));
    }
}
