package com.ram.base.repository.productos;

import com.ram.base.entity.productos.MolinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Molinos
 */
@Repository
public interface MolinoRepository extends JpaRepository<MolinoEntity, Long> {

    /**
     * Busca molinos activos
     */
    List<MolinoEntity> findByEstatusTrue();

    /**
     * Busca molinos por nombre (case insensitive, contiene)
     */
    List<MolinoEntity> findByMolinoContainingIgnoreCase(String molino);

    /**
     * Verifica si existe un molino con el nombre dado (case insensitive)
     */
    boolean existsByMolinoIgnoreCase(String molino);

    /**
     * Busca un molino por nombre exacto (case insensitive)
     */
    Optional<MolinoEntity> findByMolinoIgnoreCase(String molino);
}
