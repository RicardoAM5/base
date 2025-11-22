package com.ram.base.service.inventarios;

import com.ram.base.dto.inventarios.LocalidadDTO;
import com.ram.base.entity.inventarios.LocalidadEntity;
import com.ram.base.exception.BusinessException;
import com.ram.base.exception.ResourceNotFoundException;
import com.ram.base.mapper.LocalidadMapper;
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
 * Implementación del servicio de Localidades
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocalidadServiceImpl implements LocalidadService {

    private final LocalidadRepository localidadRepository;
    private final LocalidadMapper localidadMapper;

    /**
     * Crea una nueva localidad en el sistema
     * 
     * @param localidadDTO Datos de la localidad a crear (puede incluir áreas)
     * @return LocalidadDTO con los datos de la localidad creada (incluye ID generado)
     * @throws BusinessException si ya existe una localidad con el mismo nombre
     *                           o si hay áreas con nombres duplicados
     * 
     * Flujo de ejecución:
     * 1. Valida que el nombre de la localidad sea único en todo el sistema
     * 2. Si incluye áreas, valida que no haya nombres duplicados entre ellas
     * 3. Convierte el DTO a Entity usando el mapper
     * 4. Persiste en base de datos (genera ID automáticamente)
     * 5. Convierte la entidad guardada de vuelta a DTO y la retorna
     * 
     * Nota: @Transactional asegura que si hay un error, se hace rollback automático
     */
    @Override
    @Transactional
    public LocalidadDTO create(LocalidadDTO localidadDTO) {
        log.info("Creando nueva localidad: {}", localidadDTO.getNombre());

        // Validar que no exista una localidad con el mismo nombre
        // Usa query personalizada findByNombreIgnoreCase para búsqueda case-insensitive
        if (existsByNombre(localidadDTO.getNombre())) {
            throw new BusinessException("Ya existe una localidad con el nombre: " + localidadDTO.getNombre());
        }

        // Validar nombres únicos de áreas dentro de la localidad
        // Usa Stream API para verificar eficientemente duplicados:
        // 1. Convierte todos los nombres a minúsculas (normalización)
        // 2. distinct() elimina duplicados
        // 3. Si el conteo es diferente al tamaño original, hay duplicados
        if (localidadDTO.getAreas() != null && !localidadDTO.getAreas().isEmpty()) {
            long uniqueNames = localidadDTO.getAreas().stream()
                    .map(area -> area.getNombre().toLowerCase())
                    .distinct()
                    .count();
            
            if (uniqueNames != localidadDTO.getAreas().size()) {
                throw new BusinessException("No puede haber áreas con nombres duplicados en la misma localidad");
            }
        }

        LocalidadEntity entity = localidadMapper.toEntity(localidadDTO);
        LocalidadEntity savedEntity = localidadRepository.save(entity);

        log.info("Localidad creada exitosamente con ID: {}", savedEntity.getIdLocalidad());
        return localidadMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public LocalidadDTO update(Long id, LocalidadDTO localidadDTO) {
        log.info("Actualizando localidad con ID: {}", id);

        LocalidadEntity existingEntity = localidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Localidad", id));

        // Validar nombre único (excluyendo la misma entidad)
        if (!existingEntity.getNombre().equals(localidadDTO.getNombre()) && 
            existsByNombre(localidadDTO.getNombre())) {
            throw new BusinessException("Ya existe otra localidad con el nombre: " + localidadDTO.getNombre());
        }

        localidadMapper.updateEntity(existingEntity, localidadDTO);
        LocalidadEntity updatedEntity = localidadRepository.save(existingEntity);

        log.info("Localidad actualizada exitosamente: {}", id);
        return localidadMapper.toDTO(updatedEntity);
    }

    /**
     * Obtiene una localidad por su ID
     * 
     * @param id Identificador único de la localidad
     * @return LocalidadDTO con todos los datos (incluye áreas asociadas)
     * @throws ResourceNotFoundException si no existe localidad con ese ID
     * 
     * Optimización: @Transactional(readOnly=true) le indica a Spring que:
     * - No necesita crear snapshot de entidades para dirty checking
     * - Puede optimizar la sesión de Hibernate como solo lectura
     * - Reduce overhead de transacción (más rápido que @Transactional normal)
     */
    @Override
    @Transactional(readOnly = true)
    public LocalidadDTO getById(Long id) {
        log.debug("Buscando localidad con ID: {}", id);

        LocalidadEntity entity = localidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Localidad", id));

        return localidadMapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalidadDTO> getAll() {
        log.debug("Obteniendo todas las localidades");

        List<LocalidadEntity> entities = localidadRepository.findAll();
        return entities.stream()
                .map(localidadMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene localidades con paginación
     * 
     * @param pageable Configuración de paginación (página, tamaño, ordenamiento)
     * @return Page<LocalidadDTO> con los resultados paginados y metadatos
     * 
     * Ventajas de paginación:
     * - Solo carga N registros en memoria (no toda la tabla)
     * - Query SQL incluye LIMIT y OFFSET automáticamente
     * - Retorna metadatos útiles: totalElements, totalPages, hasNext, etc.
     * 
     * Ejemplo de uso desde controller:
     * ?page=0&size=10&sort=nombre,asc
     * 
     * Performance: Con 1 millón de registros, solo carga 10 en memoria
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LocalidadDTO> getAllPaginated(Pageable pageable) {
        log.debug("Obteniendo localidades paginadas: página {}, tamaño {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        // Spring Data JPA genera: SELECT * FROM localidad LIMIT ? OFFSET ?
        Page<LocalidadEntity> entityPage = localidadRepository.findAll(pageable);
        // map() aplica la conversión a cada elemento sin cargar todos en memoria
        return entityPage.map(localidadMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalidadDTO> getActive() {
        log.debug("Obteniendo localidades activas");

        List<LocalidadEntity> entities = localidadRepository.findByEstatusTrue();
        return entities.stream()
                .map(localidadMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalidadDTO> searchByNombre(String nombre) {
        log.debug("Buscando localidades por nombre: {}", nombre);

        List<LocalidadEntity> entities = localidadRepository.findByNombreContainingIgnoreCase(nombre);
        return entities.stream()
                .map(localidadMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LocalidadDTO activate(Long id) {
        log.info("Activando localidad con ID: {}", id);

        LocalidadEntity entity = localidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Localidad", id));

        entity.setEstatus(true);
        LocalidadEntity updatedEntity = localidadRepository.save(entity);

        log.info("Localidad activada exitosamente: {}", id);
        return localidadMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public LocalidadDTO deactivate(Long id) {
        log.info("Desactivando localidad con ID: {}", id);

        LocalidadEntity entity = localidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Localidad", id));

        entity.setEstatus(false);
        LocalidadEntity updatedEntity = localidadRepository.save(entity);

        log.info("Localidad desactivada exitosamente: {}", id);
        return localidadMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando localidad con ID: {}", id);

        if (!localidadRepository.existsById(id)) {
            throw new ResourceNotFoundException("Localidad", id);
        }

        // Soft delete: solo desactivar
        deactivate(id);
        log.info("Localidad eliminada (desactivada) exitosamente: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNombre(String nombre) {
        return localidadRepository.existsByNombreIgnoreCase(nombre);
    }
}
