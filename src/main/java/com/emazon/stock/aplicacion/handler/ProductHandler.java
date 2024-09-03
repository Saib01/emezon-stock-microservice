package com.emazon.stock.aplicacion.handler;

import com.emazon.stock.aplicacion.dtos.ProductRequest;
import com.emazon.stock.aplicacion.dtos.ProductResponse;
import com.emazon.stock.aplicacion.mapper.ProductRequestMapper;
import com.emazon.stock.aplicacion.mapper.ProductResponseMapper;
import com.emazon.stock.dominio.api.IProductServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.emazon.stock.dominio.utils.DomainConstants.PROPERTY_NAME;

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
    public Page<ProductResponse> getProductsBySearchTerm(int page, int size, String sortBy, String sortDirection) {
        return this.productResponseMapper.toProductResponsePage(
                this.productServicePort.getProductsBySearchTerm(page,size,sortBy,sortDirection),
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.fromString(sortDirection),sortBy)
                )
        );
    }

    @Override
    public ProductResponse getProduct(Long id) {
        return this.productResponseMapper.toProductResponse(
                this.productServicePort.getProduct(id)
        );
    }
}
