package com.ram.base.service.productos;

import com.ram.base.dto.productos.MolinoDTO;
import com.ram.base.entity.productos.MolinoEntity;
import com.ram.base.exception.BusinessException;
import com.ram.base.exception.ResourceNotFoundException;
import com.ram.base.mapper.MolinoMapper;
import com.ram.base.repository.productos.MolinoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Molinos
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MolinoServiceImpl implements MolinoService {

    private final MolinoRepository molinoRepository;
    private final MolinoMapper molinoMapper;

    @Override
    @Transactional
    public MolinoDTO create(MolinoDTO molinoDTO) {
        log.info("Creando nuevo molino: {}", molinoDTO.getMolino());

        if (existsByMolino(molinoDTO.getMolino())) {
            throw new BusinessException("Ya existe un molino con el nombre: " + molinoDTO.getMolino());
        }

        MolinoEntity entity = molinoMapper.toEntity(molinoDTO);
        MolinoEntity savedEntity = molinoRepository.save(entity);

        log.info("Molino creado exitosamente con ID: {}", savedEntity.getId_molino());
        return molinoMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public MolinoDTO update(Long id, MolinoDTO molinoDTO) {
        log.info("Actualizando molino con ID: {}", id);

        MolinoEntity existingEntity = molinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Molino", id));

        if (!existingEntity.getMolino().equals(molinoDTO.getMolino()) && 
            existsByMolino(molinoDTO.getMolino())) {
            throw new BusinessException("Ya existe otro molino con el nombre: " + molinoDTO.getMolino());
        }

        molinoMapper.updateEntity(existingEntity, molinoDTO);
        MolinoEntity updatedEntity = molinoRepository.save(existingEntity);

        log.info("Molino actualizado exitosamente: {}", id);
        return molinoMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public MolinoDTO getById(Long id) {
        log.debug("Buscando molino con ID: {}", id);

        MolinoEntity entity = molinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Molino", id));

        return molinoMapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MolinoDTO> getAll() {
        log.debug("Obteniendo todos los molinos");

        List<MolinoEntity> entities = molinoRepository.findAll();
        return entities.stream()
                .map(molinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MolinoDTO> getAllPaginated(Pageable pageable) {
        log.debug("Obteniendo molinos paginados: página {}, tamaño {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        Page<MolinoEntity> entityPage = molinoRepository.findAll(pageable);
        return entityPage.map(molinoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MolinoDTO> getActive() {
        log.debug("Obteniendo molinos activos");

        List<MolinoEntity> entities = molinoRepository.findByEstatusTrue();
        return entities.stream()
                .map(molinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MolinoDTO> searchByMolino(String molino) {
        log.debug("Buscando molinos por nombre: {}", molino);

        List<MolinoEntity> entities = molinoRepository.findByMolinoContainingIgnoreCase(molino);
        return entities.stream()
                .map(molinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MolinoDTO activate(Long id) {
        log.info("Activando molino con ID: {}", id);

        MolinoEntity entity = molinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Molino", id));

        entity.setEstatus(true);
        MolinoEntity updatedEntity = molinoRepository.save(entity);

        log.info("Molino activado exitosamente: {}", id);
        return molinoMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public MolinoDTO deactivate(Long id) {
        log.info("Desactivando molino con ID: {}", id);

        MolinoEntity entity = molinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Molino", id));

        entity.setEstatus(false);
        MolinoEntity updatedEntity = molinoRepository.save(entity);

        log.info("Molino desactivado exitosamente: {}", id);
        return molinoMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando molino con ID: {}", id);

        if (!molinoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Molino", id);
        }

        deactivate(id);
        log.info("Molino eliminado (desactivado) exitosamente: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMolino(String molino) {
        return molinoRepository.existsByMolinoIgnoreCase(molino);
    }
}
