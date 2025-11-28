package com.ram.base.mapper;

import com.ram.base.dto.productos.TipoDTO;
import com.ram.base.entity.productos.TipoEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades y DTOs de Tipo
 */
@Component
public class TipoMapper {

    /**
     * Convierte TipoEntity a TipoDTO
     */
    public TipoDTO toDTO(TipoEntity entity) {
        if (entity == null) {
            return null;
        }

        return TipoDTO.builder()
                .idTipo(entity.getId_tipo())
                .tipo(entity.getTipo())
                .estatus(entity.isEstatus())
                .build();
    }

    /**
     * Convierte TipoDTO a TipoEntity
     */
    public TipoEntity toEntity(TipoDTO dto) {
        if (dto == null) {
            return null;
        }

        return TipoEntity.builder()
                .id_tipo(dto.getIdTipo())
                .tipo(dto.getTipo())
                .estatus(dto.getEstatus() != null ? dto.getEstatus() : true)
                .build();
    }

    /**
     * Actualiza una entidad existente con datos del DTO
     */
    public void updateEntity(TipoEntity entity, TipoDTO dto) {
        if (entity == null || dto == null) {
            return;
        }

        entity.setTipo(dto.getTipo());
        if (dto.getEstatus() != null) {
            entity.setEstatus(dto.getEstatus());
        }
    }
}
