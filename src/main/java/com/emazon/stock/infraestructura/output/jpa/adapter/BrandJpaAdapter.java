package com.emazon.stock.infraestructura.output.jpa.adapter;

import com.emazon.stock.dominio.exeption.brand.BrandNotFoundException;
import com.emazon.stock.dominio.modelo.Brand;
import com.emazon.stock.dominio.modelo.Category;
import com.emazon.stock.dominio.modelo.PageStock;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.utils.Validator;
import com.emazon.stock.infraestructura.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.emazon.stock.dominio.utils.ConstantsDominio.PROPERTY_NAME;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;
    @Override
    public void saveBrand(Brand brand) {
        Validator.isNameAlreadyInUse(brandRepository.existsByName(brand.getName()),brand);
        this.brandRepository.save(brandEntityMapper.toBrandEntity(brand));
    }

    @Override
    public PageStock<Brand> getBrandsByName(int page, int size, String sortDirection) {
        System.out.println("JPA");
        Pageable pageable= PageRequest.of(page,size, Sort.by(Sort.Direction.fromString(sortDirection),PROPERTY_NAME.toLowerCase()));
        return brandEntityMapper.toPageStock(this.brandRepository.findAll(pageable));
    }

    @Override
    public Brand getBrand(Long id) {
        return brandEntityMapper.toBrand(
                this.brandRepository.findById(id).orElseThrow(BrandNotFoundException::new));
    }

    @Override
    public boolean findByName(String name) {
        return this.brandRepository.existsByName(name);
    }
}
