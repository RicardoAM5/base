package com.ram.base.mapper;

import com.ram.base.dto.productos.BobinaDTO;
import com.ram.base.entity.productos.*;
import com.ram.base.exception.ResourceNotFoundException;
import com.ram.base.repository.productos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades y DTOs de Bobina
 */
@Component
@RequiredArgsConstructor
public class BobinaMapper {

    private final TipoRepository tipoRepository;
    private final ClaseRepository claseRepository;
    private final MolinoRepository molinoRepository;
    private final GradoRepository gradoRepository;
    private final ProveedorRepository proveedorRepository;

    /**
     * Convierte BobinaEntity a BobinaDTO
     */
    public BobinaDTO toDTO(BobinaEntity entity) {
        if (entity == null) {
            return null;
        }

        return BobinaDTO.builder()
                .idBobina(entity.getId_bobina())
                .codigoProveedor(entity.getCodigoProveedor())
                .ancho(entity.getAncho())
                .gramaje(entity.getGramaje())
                .calibre(entity.getCalibre())
                .peso(entity.getPeso())
                .idTipo(entity.getTipoEntity() != null ? entity.getTipoEntity().getId_tipo() : null)
                .nombreTipo(entity.getTipoEntity() != null ? entity.getTipoEntity().getTipo() : null)
                .idClase(entity.getClaseEntity() != null ? entity.getClaseEntity().getId_clase() : null)
                .nombreClase(entity.getClaseEntity() != null ? entity.getClaseEntity().getClase() : null)
                .idMolino(entity.getMolinoEntity() != null ? entity.getMolinoEntity().getId_molino() : null)
                .nombreMolino(entity.getMolinoEntity() != null ? entity.getMolinoEntity().getMolino() : null)
                .idGrado(entity.getGradoEntity() != null ? entity.getGradoEntity().getId_grado() : null)
                .nombreGrado(entity.getGradoEntity() != null ? entity.getGradoEntity().getGrado() : null)
                .idProveedor(entity.getProveedor() != null ? entity.getProveedor().getId_proveedor() : null)
                .nombreProveedor(entity.getProveedor() != null ? entity.getProveedor().getNombreComercial() : null)
                .build();
    }

    /**
     * Convierte BobinaDTO a BobinaEntity
     */
    public BobinaEntity toEntity(BobinaDTO dto) {
        if (dto == null) {
            return null;
        }

        BobinaEntity entity = BobinaEntity.builder()
                .id_bobina(dto.getIdBobina())
                .codigoProveedor(dto.getCodigoProveedor())
                .ancho(dto.getAncho())
                .gramaje(dto.getGramaje())
                .calibre(dto.getCalibre())
                .peso(dto.getPeso())
                .build();

        // Establecer relaciones
        if (dto.getIdTipo() != null) {
            TipoEntity tipo = tipoRepository.findById(dto.getIdTipo())
                    .orElseThrow(() -> new ResourceNotFoundException("Tipo", dto.getIdTipo()));
            entity.setTipoEntity(tipo);
        }

        if (dto.getIdClase() != null) {
            ClaseEntity clase = claseRepository.findById(dto.getIdClase())
                    .orElseThrow(() -> new ResourceNotFoundException("Clase", dto.getIdClase()));
            entity.setClaseEntity(clase);
        }

        if (dto.getIdMolino() != null) {
            MolinoEntity molino = molinoRepository.findById(dto.getIdMolino())
                    .orElseThrow(() -> new ResourceNotFoundException("Molino", dto.getIdMolino()));
            entity.setMolinoEntity(molino);
        }

        if (dto.getIdGrado() != null) {
            GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                    .orElseThrow(() -> new ResourceNotFoundException("Grado", dto.getIdGrado()));
            entity.setGradoEntity(grado);
        }

        if (dto.getIdProveedor() != null) {
            ProveedorEntity proveedor = proveedorRepository.findById(dto.getIdProveedor())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor", dto.getIdProveedor()));
            entity.setProveedor(proveedor);
        }

        return entity;
    }

    /**
     * Actualiza una entidad existente con datos del DTO
     */
    public void updateEntity(BobinaEntity entity, BobinaDTO dto) {
        if (entity == null || dto == null) {
            return;
        }

        entity.setCodigoProveedor(dto.getCodigoProveedor());
        entity.setAncho(dto.getAncho());
        entity.setGramaje(dto.getGramaje());
        entity.setCalibre(dto.getCalibre());
        entity.setPeso(dto.getPeso());

        // Actualizar relaciones
        if (dto.getIdTipo() != null) {
            TipoEntity tipo = tipoRepository.findById(dto.getIdTipo())
                    .orElseThrow(() -> new ResourceNotFoundException("Tipo", dto.getIdTipo()));
            entity.setTipoEntity(tipo);
        }

        if (dto.getIdClase() != null) {
            ClaseEntity clase = claseRepository.findById(dto.getIdClase())
                    .orElseThrow(() -> new ResourceNotFoundException("Clase", dto.getIdClase()));
            entity.setClaseEntity(clase);
        }

        if (dto.getIdMolino() != null) {
            MolinoEntity molino = molinoRepository.findById(dto.getIdMolino())
                    .orElseThrow(() -> new ResourceNotFoundException("Molino", dto.getIdMolino()));
            entity.setMolinoEntity(molino);
        }

        if (dto.getIdGrado() != null) {
            GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                    .orElseThrow(() -> new ResourceNotFoundException("Grado", dto.getIdGrado()));
            entity.setGradoEntity(grado);
        }

        if (dto.getIdProveedor() != null) {
            ProveedorEntity proveedor = proveedorRepository.findById(dto.getIdProveedor())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor", dto.getIdProveedor()));
            entity.setProveedor(proveedor);
        }
    }
}
