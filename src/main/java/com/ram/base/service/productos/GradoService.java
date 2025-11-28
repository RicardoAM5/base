package com.ram.base.service.productos;

import com.ram.base.dto.productos.GradoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz de servicio para Grados
 */
public interface GradoService {

    /**
     * Crea un nuevo grado
     */
    GradoDTO create(GradoDTO gradoDTO);

    /**
     * Actualiza un grado existente
     */
    GradoDTO update(Long id, GradoDTO gradoDTO);

    /**
     * Obtiene un grado por ID
     */
    GradoDTO getById(Long id);

    /**
     * Obtiene todos los grados
     */
    List<GradoDTO> getAll();

    /**
     * Obtiene grados con paginación
     */
    Page<GradoDTO> getAllPaginated(Pageable pageable);

    /**
     * Obtiene grados activos
     */
    List<GradoDTO> getActive();

    /**
     * Busca grados por nombre
     */
    List<GradoDTO> searchByGrado(String grado);

    /**
     * Activa un grado
     */
    GradoDTO activate(Long id);

    /**
     * Desactiva un grado
     */
    GradoDTO deactivate(Long id);

    /**
     * Elimina un grado (soft delete)
     */
    void delete(Long id);

    /**
     * Verifica si existe un grado por nombre
     */
    boolean existsByGrado(String grado);
}
