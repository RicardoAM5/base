package com.ram.base.mapper;

import com.ram.base.dto.inventarios.AreaDTO;
import com.ram.base.dto.inventarios.LocalidadDTO;
import com.ram.base.entity.inventarios.AreaEntity;
import com.ram.base.entity.inventarios.LocalidadEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper para convertir entre entidades y DTOs de Localidad
 */
@Component
public class LocalidadMapper {

    /**
     * Convierte LocalidadEntity (modelo de base de datos) a LocalidadDTO (modelo de API)
     * 
     * ¿Por qué usar DTOs en lugar de exponer Entities directamente?
     * 1. SEGURIDAD: Controlas exactamente qué campos se exponen (no expones IDs internos)
     * 2. DESACOPLAMIENTO: Puedes cambiar la BD sin afectar el API
     * 3. PERFORMANCE: Evitas lazy loading exceptions en JSON serialization
     * 4. FLEXIBILIDAD: Puedes combinar datos de múltiples entidades
     * 
     * Este método:
     * - Usa Builder pattern de Lombok para código limpio
     * - Convierte la lista de áreas usando Stream API
     * - Maneja null safety (si no hay áreas, retorna null en lugar de error)
     * 
     * @param entity Entidad de base de datos (puede incluir lazy-loaded areas)
     * @return DTO listo para serializar a JSON
     */
    public LocalidadDTO toDTO(LocalidadEntity entity) {
        if (entity == null) {
            return null;
        }

        return LocalidadDTO.builder()
                .idLocalidad(entity.getIdLocalidad())
                .nombre(entity.getNombre())
                .estatus(entity.getEstatus())
                // Convierte List<AreaEntity> a List<AreaDTO> usando Stream API
                .areas(entity.getAreas() != null ? 
                    entity.getAreas().stream()
                        .map(this::areaToDTO)  // Method reference: más limpio que lambda
                        .collect(Collectors.toList()) : 
                    null)
                .build();
    }

    /**
     * Convierte LocalidadDTO (datos de API) a LocalidadEntity (modelo de BD)
     * 
     * IMPORTANTE - Manejo de relación bidireccional:
     * - Localidad tiene @OneToMany hacia Area
     * - Area tiene @ManyToOne hacia Localidad
     * - AMBOS lados deben estar sincronizados
     * 
     * Por eso usamos entity.addArea() en lugar de entity.getAreas().add()
     * El método addArea() hace:
     *   1. Agrega el área a la lista de la localidad
     *   2. Establece la localidad en el área (área.setLocalidad(this))
     * 
     * Si no sincronizas ambos lados:
     * - JPA puede no persistir la relación correctamente
     * - Pueden aparecer null references inesperados
     * - Los cascades pueden no funcionar
     * 
     * @param dto DTO recibido del controller
     * @return Entity lista para persistir con JPA
     */
    public LocalidadEntity toEntity(LocalidadDTO dto) {
        if (dto == null) {
            return null;
        }

        LocalidadEntity entity = LocalidadEntity.builder()
                .idLocalidad(dto.getIdLocalidad())
                .nombre(dto.getNombre())
                .estatus(dto.getEstatus() != null ? dto.getEstatus() : true)  // Default true
                .build();

        // Convertir y agregar áreas manteniendo la relación bidireccional
        if (dto.getAreas() != null && !dto.getAreas().isEmpty()) {
            dto.getAreas().forEach(areaDTO -> {
                AreaEntity areaEntity = AreaEntity.builder()
                        .nombre(areaDTO.getNombre())
                        .estatus(areaDTO.getEstatus() != null ? areaDTO.getEstatus() : true)
                        .build();
                // addArea() sincroniza AMBOS lados de la relación automáticamente
                entity.addArea(areaEntity);
            });
        }

        return entity;
    }

    /**
     * Convierte AreaEntity a AreaDTO
     */
    public AreaDTO areaToDTO(AreaEntity entity) {
        if (entity == null) {
            return null;
        }

        return AreaDTO.builder()
                .idArea(entity.getIdArea())
                .nombre(entity.getNombre())
                .idLocalidad(entity.getLocalidad() != null ? entity.getLocalidad().getIdLocalidad() : null)
                .nombreLocalidad(entity.getLocalidad() != null ? entity.getLocalidad().getNombre() : null)
                .estatus(entity.getEstatus())
                .build();
    }

    /**
     * Actualiza una entidad existente con datos del DTO
     */
    public void updateEntity(LocalidadEntity entity, LocalidadDTO dto) {
        if (entity == null || dto == null) {
            return;
        }

        entity.setNombre(dto.getNombre());
        if (dto.getEstatus() != null) {
            entity.setEstatus(dto.getEstatus());
        }
    }
}
