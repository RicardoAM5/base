package com.ram.base.repository.productos;

import com.ram.base.entity.productos.TipoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Tipos de producto
 */
@Repository
public interface TipoRepository extends JpaRepository<TipoEntity, Long> {

    /**
     * Busca tipos activos
     */
    List<TipoEntity> findByEstatusTrue();

    /**
     * Busca tipos por nombre (case insensitive, contiene)
     */
    List<TipoEntity> findByTipoContainingIgnoreCase(String tipo);

    /**
     * Verifica si existe un tipo con el nombre dado (case insensitive)
     */
    boolean existsByTipoIgnoreCase(String tipo);

    /**
     * Busca un tipo por nombre exacto (case insensitive)
     */
    Optional<TipoEntity> findByTipoIgnoreCase(String tipo);
}
