package com.ram.base.repository.inventarios;

import com.ram.base.entity.inventarios.LocalidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Localidades
 */
@Repository
public interface LocalidadRepository extends JpaRepository<LocalidadEntity, Long> {

    /**
     * Busca localidades activas
     */
    List<LocalidadEntity> findByEstatusTrue();

    /**
     * Busca localidades por nombre (case insensitive, contiene)
     */
    List<LocalidadEntity> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Verifica si existe una localidad con el nombre dado (case insensitive)
     */
    boolean existsByNombreIgnoreCase(String nombre);

    /**
     * Busca una localidad por nombre exacto (case insensitive)
     */
    Optional<LocalidadEntity> findByNombreIgnoreCase(String nombre);
}
