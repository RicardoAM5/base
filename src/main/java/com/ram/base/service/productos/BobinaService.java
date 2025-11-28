package com.ram.base.service.productos;

import com.ram.base.dto.productos.BobinaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz de servicio para Bobinas
 */
public interface BobinaService {

    /**
     * Crea una nueva bobina
     */
    BobinaDTO create(BobinaDTO bobinaDTO);

    /**
     * Actualiza una bobina existente
     */
    BobinaDTO update(Long id, BobinaDTO bobinaDTO);

    /**
     * Obtiene una bobina por ID
     */
    BobinaDTO getById(Long id);

    /**
     * Obtiene todas las bobinas
     */
    List<BobinaDTO> getAll();

    /**
     * Obtiene bobinas con paginación
     */
    Page<BobinaDTO> getAllPaginated(Pageable pageable);

    /**
     * Busca bobinas por código de proveedor
     */
    List<BobinaDTO> searchByCodigoProveedor(String codigoProveedor);

    /**
     * Busca bobinas por proveedor
     */
    List<BobinaDTO> getByProveedor(Long idProveedor);

    /**
     * Busca bobinas por tipo
     */
    List<BobinaDTO> getByTipo(Long idTipo);

    /**
     * Busca bobinas por clase
     */
    List<BobinaDTO> getByClase(Long idClase);

    /**
     * Busca bobinas por molino
     */
    List<BobinaDTO> getByMolino(Long idMolino);

    /**
     * Busca bobinas por grado
     */
    List<BobinaDTO> getByGrado(Long idGrado);

    /**
     * Busca bobinas por rango de ancho
     */
    List<BobinaDTO> getByAnchoRange(Double anchoMin, Double anchoMax);

    /**
     * Busca bobinas por rango de gramaje
     */
    List<BobinaDTO> getByGramajeRange(Double gramajeMin, Double gramajeMax);

    /**
     * Elimina una bobina
     */
    void delete(Long id);

    /**
     * Verifica si existe una bobina por código de proveedor
     */
    boolean existsByCodigoProveedor(String codigoProveedor);
}
