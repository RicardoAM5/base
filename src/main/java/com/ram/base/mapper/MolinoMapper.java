package com.ram.base.mapper;

import com.ram.base.dto.productos.MolinoDTO;
import com.ram.base.entity.productos.MolinoEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades y DTOs de Molino
 */
@Component
public class MolinoMapper {

    /**
     * Convierte MolinoEntity a MolinoDTO
     */
    public MolinoDTO toDTO(MolinoEntity entity) {
        if (entity == null) {
            return null;
        }

        return MolinoDTO.builder()
                .idMolino(entity.getId_molino())
                .molino(entity.getMolino())
                .descripcion(entity.getDescripcion())
                .estatus(entity.isEstatus())
                .build();
    }

    /**
     * Convierte MolinoDTO a MolinoEntity
     */
    public MolinoEntity toEntity(MolinoDTO dto) {
        if (dto == null) {
            return null;
        }

        return MolinoEntity.builder()
                .id_molino(dto.getIdMolino())
                .molino(dto.getMolino())
                .descripcion(dto.getDescripcion())
                .estatus(dto.getEstatus() != null ? dto.getEstatus() : true)
                .build();
    }

    /**
     * Actualiza una entidad existente con datos del DTO
     */
    public void updateEntity(MolinoEntity entity, MolinoDTO dto) {
        if (entity == null || dto == null) {
            return;
        }

        entity.setMolino(dto.getMolino());
        entity.setDescripcion(dto.getDescripcion());
        if (dto.getEstatus() != null) {
            entity.setEstatus(dto.getEstatus());
        }
    }
}
