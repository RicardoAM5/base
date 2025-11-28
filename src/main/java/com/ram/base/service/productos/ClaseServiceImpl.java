package com.ram.base.service.productos;

import com.ram.base.dto.productos.ClaseDTO;
import com.ram.base.entity.productos.ClaseEntity;
import com.ram.base.exception.BusinessException;
import com.ram.base.exception.ResourceNotFoundException;
import com.ram.base.mapper.ClaseMapper;
import com.ram.base.repository.productos.ClaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Clases
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClaseServiceImpl implements ClaseService {

    private final ClaseRepository claseRepository;
    private final ClaseMapper claseMapper;

    @Override
    @Transactional
    public ClaseDTO create(ClaseDTO claseDTO) {
        log.info("Creando nueva clase: {}", claseDTO.getClase());

        if (existsByClase(claseDTO.getClase())) {
            throw new BusinessException("Ya existe una clase con el nombre: " + claseDTO.getClase());
        }

        ClaseEntity entity = claseMapper.toEntity(claseDTO);
        ClaseEntity savedEntity = claseRepository.save(entity);

        log.info("Clase creada exitosamente con ID: {}", savedEntity.getId_clase());
        return claseMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public ClaseDTO update(Long id, ClaseDTO claseDTO) {
        log.info("Actualizando clase con ID: {}", id);

        ClaseEntity existingEntity = claseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clase", id));

        if (!existingEntity.getClase().equals(claseDTO.getClase()) && 
            existsByClase(claseDTO.getClase())) {
            throw new BusinessException("Ya existe otra clase con el nombre: " + claseDTO.getClase());
        }

        claseMapper.updateEntity(existingEntity, claseDTO);
        ClaseEntity updatedEntity = claseRepository.save(existingEntity);

        log.info("Clase actualizada exitosamente: {}", id);
        return claseMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ClaseDTO getById(Long id) {
        log.debug("Buscando clase con ID: {}", id);

        ClaseEntity entity = claseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clase", id));

        return claseMapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseDTO> getAll() {
        log.debug("Obteniendo todas las clases");

        List<ClaseEntity> entities = claseRepository.findAll();
        return entities.stream()
                .map(claseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClaseDTO> getAllPaginated(Pageable pageable) {
        log.debug("Obteniendo clases paginadas: página {}, tamaño {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ClaseEntity> entityPage = claseRepository.findAll(pageable);
        return entityPage.map(claseMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseDTO> getActive() {
        log.debug("Obteniendo clases activas");

        List<ClaseEntity> entities = claseRepository.findByEstatusTrue();
        return entities.stream()
                .map(claseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseDTO> searchByClase(String clase) {
        log.debug("Buscando clases por nombre: {}", clase);

        List<ClaseEntity> entities = claseRepository.findByClaseContainingIgnoreCase(clase);
        return entities.stream()
                .map(claseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClaseDTO activate(Long id) {
        log.info("Activando clase con ID: {}", id);

        ClaseEntity entity = claseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clase", id));

        entity.setEstatus(true);
        ClaseEntity updatedEntity = claseRepository.save(entity);

        log.info("Clase activada exitosamente: {}", id);
        return claseMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public ClaseDTO deactivate(Long id) {
        log.info("Desactivando clase con ID: {}", id);

        ClaseEntity entity = claseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clase", id));

        entity.setEstatus(false);
        ClaseEntity updatedEntity = claseRepository.save(entity);

        log.info("Clase desactivada exitosamente: {}", id);
        return claseMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando clase con ID: {}", id);

        if (!claseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Clase", id);
        }

        deactivate(id);
        log.info("Clase eliminada (desactivada) exitosamente: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByClase(String clase) {
        return claseRepository.existsByClaseIgnoreCase(clase);
    }
}
