package com.ram.base.mapper;

import com.ram.base.dto.productos.GradoDTO;
import com.ram.base.entity.productos.GradoEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades y DTOs de Grado
 */
@Component
public class GradoMapper {

    /**
     * Convierte GradoEntity a GradoDTO
     */
    public GradoDTO toDTO(GradoEntity entity) {
        if (entity == null) {
            return null;
        }

        return GradoDTO.builder()
                .idGrado(entity.getId_grado())
                .grado(entity.getGrado())
                .descripcion(entity.getDescripcion())
                .estatus(entity.isEstatus())
                .build();
    }

    /**
     * Convierte GradoDTO a GradoEntity
     */
    public GradoEntity toEntity(GradoDTO dto) {
        if (dto == null) {
            return null;
        }

        return GradoEntity.builder()
                .id_grado(dto.getIdGrado())
                .grado(dto.getGrado())
                .descripcion(dto.getDescripcion())
                .estatus(dto.getEstatus() != null ? dto.getEstatus() : true)
                .build();
    }

    /**
     * Actualiza una entidad existente con datos del DTO
     */
    public void updateEntity(GradoEntity entity, GradoDTO dto) {
        if (entity == null || dto == null) {
            return;
        }

        entity.setGrado(dto.getGrado());
        entity.setDescripcion(dto.getDescripcion());
        if (dto.getEstatus() != null) {
            entity.setEstatus(dto.getEstatus());
        }
    }
}
