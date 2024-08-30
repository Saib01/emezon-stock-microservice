package com.emazon.stock.infraestructura.configuration;

import com.emazon.stock.dominio.api.IBrandServicePort;
import com.emazon.stock.dominio.api.ICategoryServicePort;
import com.emazon.stock.dominio.spi.IBrandPersistencePort;
import com.emazon.stock.dominio.spi.ICategoryPersistencePort;
import com.emazon.stock.dominio.usecase.BrandUseCase;
import com.emazon.stock.dominio.usecase.CategoryUseCase;
import com.emazon.stock.infraestructura.output.jpa.adapter.BrandJpaAdapter;
import com.emazon.stock.infraestructura.output.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock.infraestructura.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.IBrandRepository;
import com.emazon.stock.infraestructura.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Bean
    ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }
}
