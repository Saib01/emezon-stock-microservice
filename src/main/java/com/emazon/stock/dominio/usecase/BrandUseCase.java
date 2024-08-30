package com.emazon.stock.dominio.usecase;


import com.emazon.stock.dominio.api.IBrandServicePort;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.utils.Validator;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand) {
        Validator.nameAndDescription(brand);
        Validator.isNameAlreadyInUse(this.brandPersistencePort.findByName(brand.getName()),brand);
        this.brandPersistencePort.saveBrand(brand);
    }
    @Override
    public Brand getBrand(Long id) {
        return brandPersistencePort.getBrand(id);
    }
}
