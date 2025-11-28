package com.ram.base.service.productos;

import com.ram.base.dto.productos.TipoDTO;
import com.ram.base.entity.productos.TipoEntity;
import com.ram.base.exception.BusinessException;
import com.ram.base.exception.ResourceNotFoundException;
import com.ram.base.mapper.TipoMapper;
import com.ram.base.repository.productos.TipoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Tipos
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TipoServiceImpl implements TipoService {

    private final TipoRepository tipoRepository;
    private final TipoMapper tipoMapper;

    @Override
    @Transactional
    public TipoDTO create(TipoDTO tipoDTO) {
        log.info("Creando nuevo tipo: {}", tipoDTO.getTipo());

        if (existsByTipo(tipoDTO.getTipo())) {
            throw new BusinessException("Ya existe un tipo con el nombre: " + tipoDTO.getTipo());
        }

        TipoEntity entity = tipoMapper.toEntity(tipoDTO);
        TipoEntity savedEntity = tipoRepository.save(entity);

        log.info("Tipo creado exitosamente con ID: {}", savedEntity.getId_tipo());
        return tipoMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public TipoDTO update(Long id, TipoDTO tipoDTO) {
        log.info("Actualizando tipo con ID: {}", id);

        TipoEntity existingEntity = tipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo", id));

        if (!existingEntity.getTipo().equals(tipoDTO.getTipo()) && 
            existsByTipo(tipoDTO.getTipo())) {
            throw new BusinessException("Ya existe otro tipo con el nombre: " + tipoDTO.getTipo());
        }

        tipoMapper.updateEntity(existingEntity, tipoDTO);
        TipoEntity updatedEntity = tipoRepository.save(existingEntity);

        log.info("Tipo actualizado exitosamente: {}", id);
        return tipoMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoDTO getById(Long id) {
        log.debug("Buscando tipo con ID: {}", id);

        TipoEntity entity = tipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo", id));

        return tipoMapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDTO> getAll() {
        log.debug("Obteniendo todos los tipos");

        List<TipoEntity> entities = tipoRepository.findAll();
        return entities.stream()
                .map(tipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoDTO> getAllPaginated(Pageable pageable) {
        log.debug("Obteniendo tipos paginados: página {}, tamaño {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        Page<TipoEntity> entityPage = tipoRepository.findAll(pageable);
        return entityPage.map(tipoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDTO> getActive() {
        log.debug("Obteniendo tipos activos");

        List<TipoEntity> entities = tipoRepository.findByEstatusTrue();
        return entities.stream()
                .map(tipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDTO> searchByTipo(String tipo) {
        log.debug("Buscando tipos por nombre: {}", tipo);

        List<TipoEntity> entities = tipoRepository.findByTipoContainingIgnoreCase(tipo);
        return entities.stream()
                .map(tipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TipoDTO activate(Long id) {
        log.info("Activando tipo con ID: {}", id);

        TipoEntity entity = tipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo", id));

        entity.setEstatus(true);
        TipoEntity updatedEntity = tipoRepository.save(entity);

        log.info("Tipo activado exitosamente: {}", id);
        return tipoMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public TipoDTO deactivate(Long id) {
        log.info("Desactivando tipo con ID: {}", id);

        TipoEntity entity = tipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo", id));

        entity.setEstatus(false);
        TipoEntity updatedEntity = tipoRepository.save(entity);

        log.info("Tipo desactivado exitosamente: {}", id);
        return tipoMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando tipo con ID: {}", id);

        if (!tipoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tipo", id);
        }

        deactivate(id);
        log.info("Tipo eliminado (desactivado) exitosamente: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTipo(String tipo) {
        return tipoRepository.existsByTipoIgnoreCase(tipo);
    }
}
