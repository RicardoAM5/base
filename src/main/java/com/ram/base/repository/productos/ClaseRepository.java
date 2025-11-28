package com.ram.base.repository.productos;

import com.ram.base.entity.productos.ClaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Clases de producto
 */
@Repository
public interface ClaseRepository extends JpaRepository<ClaseEntity, Long> {

    /**
     * Busca clases activas
     */
    List<ClaseEntity> findByEstatusTrue();

    /**
     * Busca clases por nombre (case insensitive, contiene)
     */
    List<ClaseEntity> findByClaseContainingIgnoreCase(String clase);

    /**
     * Verifica si existe una clase con el nombre dado (case insensitive)
     */
    boolean existsByClaseIgnoreCase(String clase);

    /**
     * Busca una clase por nombre exacto (case insensitive)
     */
    Optional<ClaseEntity> findByClaseIgnoreCase(String clase);
}
