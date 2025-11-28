package com.ram.base.mapper;

import com.ram.base.dto.productos.ClaseDTO;
import com.ram.base.entity.productos.ClaseEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades y DTOs de Clase
 */
@Component
public class ClaseMapper {

    /**
     * Convierte ClaseEntity a ClaseDTO
     */
    public ClaseDTO toDTO(ClaseEntity entity) {
        if (entity == null) {
            return null;
        }

        return ClaseDTO.builder()
                .idClase(entity.getId_clase())
                .clase(entity.getClase())
                .estatus(entity.isEstatus())
                .build();
    }

    /**
     * Convierte ClaseDTO a ClaseEntity
     */
    public ClaseEntity toEntity(ClaseDTO dto) {
        if (dto == null) {
            return null;
        }

        return ClaseEntity.builder()
                .id_clase(dto.getIdClase())
                .clase(dto.getClase())
                .estatus(dto.getEstatus() != null ? dto.getEstatus() : true)
                .build();
    }

    /**
     * Actualiza una entidad existente con datos del DTO
     */
    public void updateEntity(ClaseEntity entity, ClaseDTO dto) {
        if (entity == null || dto == null) {
            return;
        }

        entity.setClase(dto.getClase());
        if (dto.getEstatus() != null) {
            entity.setEstatus(dto.getEstatus());
        }
    }
}
