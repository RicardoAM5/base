package com.ram.base.service.productos;

import com.ram.base.dto.productos.GradoDTO;
import com.ram.base.entity.productos.GradoEntity;
import com.ram.base.exception.BusinessException;
import com.ram.base.exception.ResourceNotFoundException;
import com.ram.base.mapper.GradoMapper;
import com.ram.base.repository.productos.GradoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Grados
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GradoServiceImpl implements GradoService {

    private final GradoRepository gradoRepository;
    private final GradoMapper gradoMapper;

    @Override
    @Transactional
    public GradoDTO create(GradoDTO gradoDTO) {
        log.info("Creando nuevo grado: {}", gradoDTO.getGrado());

        if (existsByGrado(gradoDTO.getGrado())) {
            throw new BusinessException("Ya existe un grado con el nombre: " + gradoDTO.getGrado());
        }

        GradoEntity entity = gradoMapper.toEntity(gradoDTO);
        GradoEntity savedEntity = gradoRepository.save(entity);

        log.info("Grado creado exitosamente con ID: {}", savedEntity.getId_grado());
        return gradoMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public GradoDTO update(Long id, GradoDTO gradoDTO) {
        log.info("Actualizando grado con ID: {}", id);

        GradoEntity existingEntity = gradoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grado", id));

        if (!existingEntity.getGrado().equals(gradoDTO.getGrado()) && 
            existsByGrado(gradoDTO.getGrado())) {
            throw new BusinessException("Ya existe otro grado con el nombre: " + gradoDTO.getGrado());
        }

        gradoMapper.updateEntity(existingEntity, gradoDTO);
        GradoEntity updatedEntity = gradoRepository.save(existingEntity);

        log.info("Grado actualizado exitosamente: {}", id);
        return gradoMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public GradoDTO getById(Long id) {
        log.debug("Buscando grado con ID: {}", id);

        GradoEntity entity = gradoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grado", id));

        return gradoMapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradoDTO> getAll() {
        log.debug("Obteniendo todos los grados");

        List<GradoEntity> entities = gradoRepository.findAll();
        return entities.stream()
                .map(gradoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GradoDTO> getAllPaginated(Pageable pageable) {
        log.debug("Obteniendo grados paginados: página {}, tamaño {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        Page<GradoEntity> entityPage = gradoRepository.findAll(pageable);
        return entityPage.map(gradoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradoDTO> getActive() {
        log.debug("Obteniendo grados activos");

        List<GradoEntity> entities = gradoRepository.findByEstatusTrue();
        return entities.stream()
                .map(gradoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradoDTO> searchByGrado(String grado) {
        log.debug("Buscando grados por nombre: {}", grado);

        List<GradoEntity> entities = gradoRepository.findByGradoContainingIgnoreCase(grado);
        return entities.stream()
                .map(gradoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GradoDTO activate(Long id) {
        log.info("Activando grado con ID: {}", id);

        GradoEntity entity = gradoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grado", id));

        entity.setEstatus(true);
        GradoEntity updatedEntity = gradoRepository.save(entity);

        log.info("Grado activado exitosamente: {}", id);
        return gradoMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public GradoDTO deactivate(Long id) {
        log.info("Desactivando grado con ID: {}", id);

        GradoEntity entity = gradoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grado", id));

        entity.setEstatus(false);
        GradoEntity updatedEntity = gradoRepository.save(entity);

        log.info("Grado desactivado exitosamente: {}", id);
        return gradoMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando grado con ID: {}", id);

        if (!gradoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grado", id);
        }

        deactivate(id);
        log.info("Grado eliminado (desactivado) exitosamente: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByGrado(String grado) {
        return gradoRepository.existsByGradoIgnoreCase(grado);
    }
}
