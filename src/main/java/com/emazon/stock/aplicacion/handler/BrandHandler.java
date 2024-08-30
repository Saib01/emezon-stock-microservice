package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.BrandRequest;
import com.emazon.stock.aplicacion.dtos.BrandResponse;
import com.emazon.stock.aplicacion.mapper.BrandRequestMapper;
import com.emazon.stock.aplicacion.mapper.BrandResponseMapper;
import com.emazon.stock.dominio.api.IBrandServicePort;
import com.emazon.stock.dominio.modelo.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;


@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements  IBrandHandler{
    private final IBrandServicePort brandServicePort;
    private final BrandResponseMapper brandResponseMapper;
    private final BrandRequestMapper brandRequestMapper;

    @Override
    public void saveBrand(BrandRequest brandRequest) {
        Brand brand=brandRequestMapper.toBrand(brandRequest);
        brandServicePort.saveBrand(brand);
    }

    @Override
    public Page<BrandResponse> getBrandsByName(int page, int size, String sortDirection) {
        return this.brandResponseMapper.toBrandResponsePage(
                brandServicePort.getBrandsByName(page,size,sortDirection),
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.fromString(sortDirection),PROPERTY_NAME.toLowerCase())
                )
        );
    }

    @Override
    public BrandResponse getBrand(Long id) {
        return brandResponseMapper.toBrandResponse(brandServicePort.getBrand(id));
    }
}
