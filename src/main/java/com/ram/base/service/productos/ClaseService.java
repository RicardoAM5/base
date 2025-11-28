package com.ram.base.service.productos;

import com.ram.base.dto.productos.ClaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz de servicio para Clases
 */
public interface ClaseService {

    /**
     * Crea una nueva clase
     */
    ClaseDTO create(ClaseDTO claseDTO);

    /**
     * Actualiza una clase existente
     */
    ClaseDTO update(Long id, ClaseDTO claseDTO);

    /**
     * Obtiene una clase por ID
     */
    ClaseDTO getById(Long id);

    /**
     * Obtiene todas las clases
     */
    List<ClaseDTO> getAll();

    /**
     * Obtiene clases con paginación
     */
    Page<ClaseDTO> getAllPaginated(Pageable pageable);

    /**
     * Obtiene clases activas
     */
    List<ClaseDTO> getActive();

    /**
     * Busca clases por nombre
     */
    List<ClaseDTO> searchByClase(String clase);

    /**
     * Activa una clase
     */
    ClaseDTO activate(Long id);

    /**
     * Desactiva una clase
     */
    ClaseDTO deactivate(Long id);

    /**
     * Elimina una clase (soft delete)
     */
    void delete(Long id);

    /**
     * Verifica si existe una clase por nombre
     */
    boolean existsByClase(String clase);
}
