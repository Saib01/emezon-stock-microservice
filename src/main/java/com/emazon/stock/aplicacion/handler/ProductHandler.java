package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.ProductRequest;
import com.emazon.stock.aplicacion.dtos.ProductResponse;
import com.emazon.stock.aplicacion.mapper.ProductRequestMapper;
import com.emazon.stock.aplicacion.mapper.ProductResponseMapper;
import com.emazon.stock.dominio.api.IProductServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductHandler implements IProductHandler{
    private final IProductServicePort productServicePort;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    @Override
    public void saveProduct(ProductRequest productRequest) {
        this.productServicePort.saveProduct(
                this.productRequestMapper.toProduct(productRequest)
        );
    }

    @Override
    public ProductResponse getProduct(Long id) {
        return this.productResponseMapper.toProductResponse(
                this.productServicePort.getProduct(id)
        );
    }
}
