package com.emazon.stock.dominio.usecase;


import com.emazon.stock.dominio.api.IBrandServicePort;
import com.emazon.stock.dominio.exeption.brand.BrandNotFoundException;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.dominio.utils.PageValidator;
import com.emazon.stock.dominio.utils.Validator;

import java.util.Optional;

import static com.emazon.stock.dominio.exeption.ExceptionResponse.BRAND_NOT_FOUND;
import static com.emazon.stock.dominio.utils.Direction.ASC;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand) {
        Validator.nameAndDescription(brand);
        Validator.validateNameIsAlreadyInUse(this.brandPersistencePort, brand);
        this.brandPersistencePort.saveBrand(brand);
    }

    @Override
    public PageStock<Brand> getBrandsByName(int page, int size, String sortDirection) {
        PageValidator.parameters(page, size, sortDirection, Brand.class.getSimpleName());
        PageStock<Brand> brandPageStock = this.brandPersistencePort.getBrandsByName(page, size, sortDirection);
        brandPageStock.setAscending(ASC.equalsIgnoreCase(sortDirection));
        return brandPageStock;
    }

    @Override
    public Brand getBrand(Long id) {
        return Optional.ofNullable(brandPersistencePort.getBrand(id))
                .orElseThrow(() -> new BrandNotFoundException(BRAND_NOT_FOUND)
                );
    }

    @Override
    public Boolean isBrandNameAvailable(String brandName) {
        return !this.brandPersistencePort.existsByName(brandName);
    }
}
