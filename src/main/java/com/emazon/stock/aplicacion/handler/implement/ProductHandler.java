package com.emazon.stock.aplicacion.handler.implement;

import com.emazon.stock.aplicacion.dtos.ShoppingCartListRequest;
import com.emazon.stock.aplicacion.dtos.product.ProductRequest;
import com.emazon.stock.aplicacion.dtos.product.ProductResponse;
import com.emazon.stock.aplicacion.handler.IProductHandler;
import com.emazon.stock.aplicacion.mapper.product.ProductRequestMapper;
import com.emazon.stock.aplicacion.mapper.product.ProductResponseMapper;
import com.emazon.stock.dominio.api.IProductServicePort;
import com.emazon.stock.dominio.utils.PageStock;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductHandler implements IProductHandler {
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
    public PageStock<ProductResponse> getProductsBySearchTerm(int page, int size, String sortBy, String sortDirection) {
        return this.productResponseMapper.toProductResponsePageStock(
                this.productServicePort.getProductsBySearchTerm(page, size, sortBy, sortDirection)
        );
    }

    @Override
    public ProductResponse getProduct(Long id) {
        return this.productResponseMapper.toProductResponse(
                this.productServicePort.getProduct(id)
        );
    }

    @Override
    public void addSupply(Long id, Integer supply) {
        this.productServicePort.addSupply(id, supply);
    }

    @Override
    public boolean validateMaxProductPerCategory(List<Long> listIdsProducts) {
        return this.productServicePort.validateMaxProductPerCategory(listIdsProducts);
    }

    @Override
    public PageStock<ProductResponse> getPaginatedProductsInShoppingCart(int page, int size, String sortDirection, ShoppingCartListRequest shoppingCartListRequest) {
        return this.productResponseMapper.toProductResponsePageStock(
                this.productServicePort.getPaginatedProductsInShoppingCart(
                        shoppingCartListRequest.getListIdsProducts(),
                        shoppingCartListRequest.getCategoryName(),
                        shoppingCartListRequest.getBrandName(),
                        page,
                        size,
                        sortDirection
                )
        );
    }
}
