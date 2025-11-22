package com.ram.base.repository.inventarios;

import com.ram.base.entity.inventarios.AreaEntity;
import com.ram.base.entity.inventarios.LocalidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Áreas
 */
@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Long> {

    /**
     * Busca áreas por localidad
     */
    List<AreaEntity> findByLocalidad(LocalidadEntity localidad);

    /**
     * Busca áreas por ID de localidad
     */
    List<AreaEntity> findByLocalidadIdLocalidad(Long idLocalidad);

    /**
     * Busca áreas activas por localidad
     */
    List<AreaEntity> findByLocalidadAndEstatusTrue(LocalidadEntity localidad);

    /**
     * Busca áreas por nombre (case insensitive, contiene)
     */
    List<AreaEntity> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca áreas activas
     */
    List<AreaEntity> findByEstatusTrue();

    /**
     * Verifica si existe un área con el nombre en una localidad específica
     */
    boolean existsByNombreIgnoreCaseAndLocalidadIdLocalidad(String nombre, Long idLocalidad);

    /**
     * Busca área por nombre y localidad
     */
    Optional<AreaEntity> findByNombreIgnoreCaseAndLocalidadIdLocalidad(String nombre, Long idLocalidad);

    /**
     * Cuenta áreas por localidad
     */
    long countByLocalidadIdLocalidad(Long idLocalidad);

    /**
     * Busca áreas con su localidad cargada (evita N+1)
     */
    @Query("SELECT a FROM AreaEntity a JOIN FETCH a.localidad WHERE a.idArea = :id")
    Optional<AreaEntity> findByIdWithLocalidad(@Param("id") Long id);
}
