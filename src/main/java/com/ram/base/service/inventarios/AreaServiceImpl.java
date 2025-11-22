package com.ram.base.service.inventarios;

import com.ram.base.dto.inventarios.AreaDTO;
import com.ram.base.dto.inventarios.CreateAreaRequest;
import com.ram.base.entity.inventarios.AreaEntity;
import com.ram.base.entity.inventarios.LocalidadEntity;
import com.ram.base.exception.BusinessException;
import com.ram.base.exception.ResourceNotFoundException;
import com.ram.base.mapper.LocalidadMapper;
import com.ram.base.repository.inventarios.AreaRepository;
import com.ram.base.repository.inventarios.LocalidadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Áreas
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final LocalidadRepository localidadRepository;
    private final LocalidadMapper mapper;

    /**
     * Crea una nueva área dentro de una localidad existente
     * 
     * @param request Datos del área (nombre, idLocalidad, estatus opcional)
     * @return AreaDTO con los datos del área creada
     * @throws ResourceNotFoundException si la localidad no existe
     * @throws BusinessException si ya existe un área con ese nombre en la localidad
     * 
     * Regla de negocio CRÍTICA:
     * - Un Área SIEMPRE debe estar asociada a una Localidad (obligatorio)
     * - El nombre del área debe ser único DENTRO de su localidad (no globalmente)
     * - Dos localidades diferentes SÍ pueden tener áreas con el mismo nombre
     * 
     * Ejemplo válido:
     *   Localidad "CDMX" -> Área "Almacén"
     *   Localidad "Guadalajara" -> Área "Almacén"  (permitido)
     * 
     * Ejemplo inválido:
     *   Localidad "CDMX" -> Área "Almacén"
     *   Localidad "CDMX" -> Área "almacén"  (rechazado, duplicado)
     */
    @Override
    @Transactional
    public AreaDTO create(CreateAreaRequest request) {
        log.info("Creando nueva área: {} para localidad ID: {}", 
                request.getNombre(), request.getIdLocalidad());

        // Validar que la localidad existe antes de crear el área
        // Si no existe, lanza ResourceNotFoundException que se convierte en 404
        LocalidadEntity localidad = localidadRepository.findById(request.getIdLocalidad())
                .orElseThrow(() -> new ResourceNotFoundException("Localidad", request.getIdLocalidad()));

        // Validar que no exista un área con el mismo nombre en esta localidad
        // Query generada automáticamente por Spring Data JPA:
        // SELECT COUNT(*) FROM area WHERE UPPER(nombre) = UPPER(?) AND id_localidad = ?
        // - IgnoreCase hace la comparación case-insensitive
        // - Solo busca dentro de la localidad específica (no es constraint global)
        if (areaRepository.existsByNombreIgnoreCaseAndLocalidadIdLocalidad(
                request.getNombre(), request.getIdLocalidad())) {
            throw new BusinessException(String.format(
                    "Ya existe un área con el nombre '%s' en la localidad '%s'",
                    request.getNombre(), localidad.getNombre()));
        }

        AreaEntity entity = AreaEntity.builder()
                .nombre(request.getNombre())
                .localidad(localidad)
                .estatus(request.getEstatus() != null ? request.getEstatus() : true)
                .build();

        AreaEntity savedEntity = areaRepository.save(entity);

        log.info("Área creada exitosamente con ID: {}", savedEntity.getIdArea());
        return mapper.areaToDTO(savedEntity);
    }

    @Override
    @Transactional
    public AreaDTO update(Long id, AreaDTO areaDTO) {
        log.info("Actualizando área con ID: {}", id);

        AreaEntity existingEntity = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área", id));

        // Validar nombre único en la localidad (excluyendo la misma entidad)
        if (!existingEntity.getNombre().equalsIgnoreCase(areaDTO.getNombre())) {
            Long idLocalidad = existingEntity.getLocalidad().getIdLocalidad();
            if (areaRepository.existsByNombreIgnoreCaseAndLocalidadIdLocalidad(
                    areaDTO.getNombre(), idLocalidad)) {
                throw new BusinessException(String.format(
                        "Ya existe otra área con el nombre '%s' en esta localidad",
                        areaDTO.getNombre()));
            }
        }

        existingEntity.setNombre(areaDTO.getNombre());
        if (areaDTO.getEstatus() != null) {
            existingEntity.setEstatus(areaDTO.getEstatus());
        }

        AreaEntity updatedEntity = areaRepository.save(existingEntity);

        log.info("Área actualizada exitosamente: {}", id);
        return mapper.areaToDTO(updatedEntity);
    }

    /**
     * Obtiene un área por su ID con optimización de JOIN FETCH
     * 
     * @param id Identificador del área
     * @return AreaDTO con datos del área Y su localidad
     * @throws ResourceNotFoundException si no existe el área
     * 
     * OPTIMIZACIÓN CRÍTICA - Prevención de N+1:
     * - findByIdWithLocalidad() usa @Query con "LEFT JOIN FETCH"
     * - Carga el área Y su localidad en UNA SOLA query
     * 
     * Sin JOIN FETCH (problema N+1):
     *   Query 1: SELECT * FROM area WHERE id = 1
     *   Query 2: SELECT * FROM localidad WHERE id = ? (cuando accedes a area.getLocalidad())
     *   Total: 2 queries
     * 
     * Con JOIN FETCH (optimizado):
     *   Query 1: SELECT a.*, l.* FROM area a LEFT JOIN localidad l ON a.id_localidad = l.id WHERE a.id = 1
     *   Total: 1 query (50% más rápido)
     * 
     * Si tienes 100 áreas, JOIN FETCH hace 1 query vs 101 queries sin optimización
     */
    @Override
    @Transactional(readOnly = true)
    public AreaDTO getById(Long id) {
        log.debug("Buscando área con ID: {}", id);

        // Usa query optimizada con JOIN FETCH para evitar lazy loading
        AreaEntity entity = areaRepository.findByIdWithLocalidad(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área", id));

        return mapper.areaToDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaDTO> getAll() {
        log.debug("Obteniendo todas las áreas");

        List<AreaEntity> entities = areaRepository.findAll();
        return entities.stream()
                .map(mapper::areaToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AreaDTO> getAllPaginated(Pageable pageable) {
        log.debug("Obteniendo áreas paginadas: página {}, tamaño {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        Page<AreaEntity> entityPage = areaRepository.findAll(pageable);
        return entityPage.map(mapper::areaToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaDTO> getByLocalidad(Long idLocalidad) {
        log.debug("Obteniendo áreas de localidad ID: {}", idLocalidad);

        // Validar que la localidad existe
        if (!localidadRepository.existsById(idLocalidad)) {
            throw new ResourceNotFoundException("Localidad", idLocalidad);
        }

        List<AreaEntity> entities = areaRepository.findByLocalidadIdLocalidad(idLocalidad);
        return entities.stream()
                .map(mapper::areaToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaDTO> getActive() {
        log.debug("Obteniendo áreas activas");

        List<AreaEntity> entities = areaRepository.findByEstatusTrue();
        return entities.stream()
                .map(mapper::areaToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaDTO> searchByNombre(String nombre) {
        log.debug("Buscando áreas por nombre: {}", nombre);

        List<AreaEntity> entities = areaRepository.findByNombreContainingIgnoreCase(nombre);
        return entities.stream()
                .map(mapper::areaToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AreaDTO activate(Long id) {
        log.info("Activando área con ID: {}", id);

        AreaEntity entity = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área", id));

        entity.setEstatus(true);
        AreaEntity updatedEntity = areaRepository.save(entity);

        log.info("Área activada exitosamente: {}", id);
        return mapper.areaToDTO(updatedEntity);
    }

    @Override
    @Transactional
    public AreaDTO deactivate(Long id) {
        log.info("Desactivando área con ID: {}", id);

        AreaEntity entity = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área", id));

        entity.setEstatus(false);
        AreaEntity updatedEntity = areaRepository.save(entity);

        log.info("Área desactivada exitosamente: {}", id);
        return mapper.areaToDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando área con ID: {}", id);

        if (!areaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Área", id);
        }

        // Soft delete: solo desactivar
        deactivate(id);
        log.info("Área eliminada (desactivada) exitosamente: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByLocalidad(Long idLocalidad) {
        log.debug("Contando áreas de localidad ID: {}", idLocalidad);
        return areaRepository.countByLocalidadIdLocalidad(idLocalidad);
    }
}
