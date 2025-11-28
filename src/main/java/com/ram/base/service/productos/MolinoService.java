package com.ram.base.service.productos;

import com.ram.base.dto.productos.MolinoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz de servicio para Molinos
 */
public interface MolinoService {

    /**
     * Crea un nuevo molino
     */
    MolinoDTO create(MolinoDTO molinoDTO);

    /**
     * Actualiza un molino existente
     */
    MolinoDTO update(Long id, MolinoDTO molinoDTO);

    /**
     * Obtiene un molino por ID
     */
    MolinoDTO getById(Long id);

    /**
     * Obtiene todos los molinos
     */
    List<MolinoDTO> getAll();

    /**
     * Obtiene molinos con paginación
     */
    Page<MolinoDTO> getAllPaginated(Pageable pageable);

    /**
     * Obtiene molinos activos
     */
    List<MolinoDTO> getActive();

    /**
     * Busca molinos por nombre
     */
    List<MolinoDTO> searchByMolino(String molino);

    /**
     * Activa un molino
     */
    MolinoDTO activate(Long id);

    /**
     * Desactiva un molino
     */
    MolinoDTO deactivate(Long id);

    /**
     * Elimina un molino (soft delete)
     */
    void delete(Long id);

    /**
     * Verifica si existe un molino por nombre
     */
    boolean existsByMolino(String molino);
}
