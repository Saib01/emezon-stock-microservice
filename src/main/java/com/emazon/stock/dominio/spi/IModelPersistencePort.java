package com.emazon.stock.dominio.spi;

public interface IModelPersistencePort {
    boolean existsByName(String name);
    boolean existsById(Long id);

}
