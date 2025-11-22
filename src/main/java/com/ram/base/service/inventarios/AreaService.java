package com.ram.base.service.inventarios;

import com.ram.base.dto.inventarios.AreaDTO;
import com.ram.base.dto.inventarios.CreateAreaRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz de servicio para Áreas
 */
public interface AreaService {

    /**
     * Crea una nueva área
     */
    AreaDTO create(CreateAreaRequest request);

    /**
     * Actualiza un área existente
     */
    AreaDTO update(Long id, AreaDTO areaDTO);

    /**
     * Obtiene un área por ID
     */
    AreaDTO getById(Long id);

    /**
     * Obtiene todas las áreas
     */
    List<AreaDTO> getAll();

    /**
     * Obtiene áreas con paginación
     */
    Page<AreaDTO> getAllPaginated(Pageable pageable);

    /**
     * Obtiene áreas de una localidad específica
     */
    List<AreaDTO> getByLocalidad(Long idLocalidad);

    /**
     * Obtiene áreas activas
     */
    List<AreaDTO> getActive();

    /**
     * Busca áreas por nombre
     */
    List<AreaDTO> searchByNombre(String nombre);

    /**
     * Activa un área
     */
    AreaDTO activate(Long id);

    /**
     * Desactiva un área
     */
    AreaDTO deactivate(Long id);

    /**
     * Elimina un área (soft delete)
     */
    void delete(Long id);

    /**
     * Cuenta las áreas de una localidad
     */
    long countByLocalidad(Long idLocalidad);
}
