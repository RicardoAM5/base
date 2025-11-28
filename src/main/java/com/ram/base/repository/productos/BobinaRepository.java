package com.ram.base.repository.productos;

import com.ram.base.entity.productos.BobinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para Bobinas
 */
@Repository
public interface BobinaRepository extends JpaRepository<BobinaEntity, Long> {

    /**
     * Busca bobinas por código de proveedor (case insensitive, contiene)
     */
    List<BobinaEntity> findByCodigoProveedorContainingIgnoreCase(String codigoProveedor);

    /**
     * Verifica si existe una bobina con el código de proveedor dado
     */
    boolean existsByCodigoProveedorIgnoreCase(String codigoProveedor);

    /**
     * Busca una bobina por código de proveedor exacto (case insensitive)
     */
    Optional<BobinaEntity> findByCodigoProveedorIgnoreCase(String codigoProveedor);

    /**
     * Busca bobinas por proveedor
     */
    @Query("SELECT b FROM BobinaEntity b WHERE b.proveedor.id_proveedor = :idProveedor")
    List<BobinaEntity> findByProveedor(@Param("idProveedor") Long idProveedor);

    /**
     * Busca bobinas por tipo
     */
    @Query("SELECT b FROM BobinaEntity b WHERE b.tipoEntity.id_tipo = :idTipo")
    List<BobinaEntity> findByTipo(@Param("idTipo") Long idTipo);

    /**
     * Busca bobinas por clase
     */
    @Query("SELECT b FROM BobinaEntity b WHERE b.claseEntity.id_clase = :idClase")
    List<BobinaEntity> findByClase(@Param("idClase") Long idClase);

    /**
     * Busca bobinas por molino
     */
    @Query("SELECT b FROM BobinaEntity b WHERE b.molinoEntity.id_molino = :idMolino")
    List<BobinaEntity> findByMolino(@Param("idMolino") Long idMolino);

    /**
     * Busca bobinas por grado
     */
    @Query("SELECT b FROM BobinaEntity b WHERE b.gradoEntity.id_grado = :idGrado")
    List<BobinaEntity> findByGrado(@Param("idGrado") Long idGrado);

    /**
     * Busca bobinas por rango de ancho
     */
    @Query("SELECT b FROM BobinaEntity b WHERE b.ancho BETWEEN :anchoMin AND :anchoMax")
    List<BobinaEntity> findByAnchoRange(@Param("anchoMin") Double anchoMin, @Param("anchoMax") Double anchoMax);

    /**
     * Busca bobinas por rango de gramaje
     */
    @Query("SELECT b FROM BobinaEntity b WHERE b.gramaje BETWEEN :gramajeMin AND :gramajeMax")
    List<BobinaEntity> findByGramajeRange(@Param("gramajeMin") Double gramajeMin, @Param("gramajeMax") Double gramajeMax);
}
