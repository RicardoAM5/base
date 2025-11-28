package com.ram.base.repository.productos;

import com.ram.base.entity.productos.GradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Grados de producto
 */
@Repository
public interface GradoRepository extends JpaRepository<GradoEntity, Long> {

    /**
     * Busca grados activos
     */
    List<GradoEntity> findByEstatusTrue();

    /**
     * Busca grados por nombre (case insensitive, contiene)
     */
    List<GradoEntity> findByGradoContainingIgnoreCase(String grado);

    /**
     * Verifica si existe un grado con el nombre dado (case insensitive)
     */
    boolean existsByGradoIgnoreCase(String grado);

    /**
     * Busca un grado por nombre exacto (case insensitive)
     */
    Optional<GradoEntity> findByGradoIgnoreCase(String grado);
}
