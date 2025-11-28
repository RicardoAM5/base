package com.ram.base.service.productos;

import com.ram.base.dto.productos.TipoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz de servicio para Tipos
 */
public interface TipoService {

    /**
     * Crea un nuevo tipo
     */
    TipoDTO create(TipoDTO tipoDTO);

    /**
     * Actualiza un tipo existente
     */
    TipoDTO update(Long id, TipoDTO tipoDTO);

    /**
     * Obtiene un tipo por ID
     */
    TipoDTO getById(Long id);

    /**
     * Obtiene todos los tipos
     */
    List<TipoDTO> getAll();

    /**
     * Obtiene tipos con paginación
     */
    Page<TipoDTO> getAllPaginated(Pageable pageable);

    /**
     * Obtiene tipos activos
     */
    List<TipoDTO> getActive();

    /**
     * Busca tipos por nombre
     */
    List<TipoDTO> searchByTipo(String tipo);

    /**
     * Activa un tipo
     */
    TipoDTO activate(Long id);

    /**
     * Desactiva un tipo
     */
    TipoDTO deactivate(Long id);

    /**
     * Elimina un tipo (soft delete)
     */
    void delete(Long id);

    /**
     * Verifica si existe un tipo por nombre
     */
    boolean existsByTipo(String tipo);
}
