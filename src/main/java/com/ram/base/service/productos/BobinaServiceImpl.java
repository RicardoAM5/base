package com.ram.base.service.productos;

import com.ram.base.dto.productos.BobinaDTO;
import com.ram.base.entity.productos.BobinaEntity;
import com.ram.base.exception.BusinessException;
import com.ram.base.exception.ResourceNotFoundException;
import com.ram.base.mapper.BobinaMapper;
import com.ram.base.repository.productos.BobinaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Bobinas
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BobinaServiceImpl implements BobinaService {

    private final BobinaRepository bobinaRepository;
    private final BobinaMapper bobinaMapper;

    @Override
    @Transactional
    public BobinaDTO create(BobinaDTO bobinaDTO) {
        log.info("Creando nueva bobina con código: {}", bobinaDTO.getCodigoProveedor());

        if (existsByCodigoProveedor(bobinaDTO.getCodigoProveedor())) {
            throw new BusinessException("Ya existe una bobina con el código: " + bobinaDTO.getCodigoProveedor());
        }

        BobinaEntity entity = bobinaMapper.toEntity(bobinaDTO);
        BobinaEntity savedEntity = bobinaRepository.save(entity);

        log.info("Bobina creada exitosamente con ID: {}", savedEntity.getId_bobina());
        return bobinaMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public BobinaDTO update(Long id, BobinaDTO bobinaDTO) {
        log.info("Actualizando bobina con ID: {}", id);

        BobinaEntity existingEntity = bobinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bobina", id));

        if (!existingEntity.getCodigoProveedor().equals(bobinaDTO.getCodigoProveedor()) && 
            existsByCodigoProveedor(bobinaDTO.getCodigoProveedor())) {
            throw new BusinessException("Ya existe otra bobina con el código: " + bobinaDTO.getCodigoProveedor());
        }

        bobinaMapper.updateEntity(existingEntity, bobinaDTO);
        BobinaEntity updatedEntity = bobinaRepository.save(existingEntity);

        log.info("Bobina actualizada exitosamente: {}", id);
        return bobinaMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public BobinaDTO getById(Long id) {
        log.debug("Buscando bobina con ID: {}", id);

        BobinaEntity entity = bobinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bobina", id));

        return bobinaMapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BobinaDTO> getAll() {
        log.debug("Obteniendo todas las bobinas");

        List<BobinaEntity> entities = bobinaRepository.findAll();
        return entities.stream()
                .map(bobinaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BobinaDTO> getAllPaginated(Pageable pageable) {
        log.debug("Obteniendo bobinas paginadas: página {}, tamaño {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        Page<BobinaEntity> entityPage = bobinaRepository.findAll(pageable);
        return entityPage.map(bobinaMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BobinaDTO> searchByCodigoProveedor(String codigoProveedor) {
        log.debug("Buscando bobinas por código de proveedor: {}", codigoProveedor);

        List<BobinaEntity> entities = bobinaRepository.findByCodigoProveedorContainingIgnoreCase(codigoProveedor);
        return entities.stream()
                .map(bobinaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BobinaDTO> getByProveedor(Long idProveedor) {
        log.debug("Buscando bobinas por proveedor ID: {}", idProveedor);

        List<BobinaEntity> entities = bobinaRepository.findByProveedor(idProveedor);
        return entities.stream()
                .map(bobinaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BobinaDTO> getByTipo(Long idTipo) {
        log.debug("Buscando bobinas por tipo ID: {}", idTipo);

        List<BobinaEntity> entities = bobinaRepository.findByTipo(idTipo);
        return entities.stream()
                .map(bobinaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BobinaDTO> getByClase(Long idClase) {
        log.debug("Buscando bobinas por clase ID: {}", idClase);

        List<BobinaEntity> entities = bobinaRepository.findByClase(idClase);
        return entities.stream()
                .map(bobinaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BobinaDTO> getByMolino(Long idMolino) {
        log.debug("Buscando bobinas por molino ID: {}", idMolino);

        List<BobinaEntity> entities = bobinaRepository.findByMolino(idMolino);
        return entities.stream()
                .map(bobinaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BobinaDTO> getByGrado(Long idGrado) {
        log.debug("Buscando bobinas por grado ID: {}", idGrado);

        List<BobinaEntity> entities = bobinaRepository.findByGrado(idGrado);
        return entities.stream()
                .map(bobinaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BobinaDTO> getByAnchoRange(Double anchoMin, Double anchoMax) {
        log.debug("Buscando bobinas por rango de ancho: {} - {}", anchoMin, anchoMax);

        List<BobinaEntity> entities = bobinaRepository.findByAnchoRange(anchoMin, anchoMax);
        return entities.stream()
                .map(bobinaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BobinaDTO> getByGramajeRange(Double gramajeMin, Double gramajeMax) {
        log.debug("Buscando bobinas por rango de gramaje: {} - {}", gramajeMin, gramajeMax);

        List<BobinaEntity> entities = bobinaRepository.findByGramajeRange(gramajeMin, gramajeMax);
        return entities.stream()
                .map(bobinaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando bobina con ID: {}", id);

        if (!bobinaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bobina", id);
        }

        bobinaRepository.deleteById(id);
        log.info("Bobina eliminada exitosamente: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCodigoProveedor(String codigoProveedor) {
        return bobinaRepository.existsByCodigoProveedorIgnoreCase(codigoProveedor);
    }
}
