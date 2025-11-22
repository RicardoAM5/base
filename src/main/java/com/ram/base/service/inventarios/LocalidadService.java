package com.ram.base.service.inventarios;

import com.ram.base.dto.inventarios.LocalidadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz de servicio para Localidades
 */
public interface LocalidadService {

    /**
     * Crea una nueva localidad con sus áreas
     */
    LocalidadDTO create(LocalidadDTO localidadDTO);

    /**
     * Actualiza una localidad existente
     */
    LocalidadDTO update(Long id, LocalidadDTO localidadDTO);

    /**
     * Obtiene una localidad por ID
     */
    LocalidadDTO getById(Long id);

    /**
     * Obtiene todas las localidades
     */
    List<LocalidadDTO> getAll();

    /**
     * Obtiene localidades con paginación
     */
    Page<LocalidadDTO> getAllPaginated(Pageable pageable);

    /**
     * Obtiene localidades activas
     */
    List<LocalidadDTO> getActive();

    /**
     * Busca localidades por nombre
     */
    List<LocalidadDTO> searchByNombre(String nombre);

    /**
     * Activa una localidad
     */
    LocalidadDTO activate(Long id);

    /**
     * Desactiva una localidad
     */
    LocalidadDTO deactivate(Long id);

    /**
     * Elimina una localidad (soft delete)
     */
    void delete(Long id);

    /**
     * Verifica si existe una localidad por nombre
     */
    boolean existsByNombre(String nombre);
}
