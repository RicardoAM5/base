package com.ram.base.entity.inventarios;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una localidad geográfica.
 * Una localidad puede contener múltiples áreas.
 */
@Entity
@Table(name = "localidad", indexes = {
    @Index(name = "idx_localidad_nombre", columnList = "nombre"),
    @Index(name = "idx_localidad_estatus", columnList = "estatus")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una ubicación geográfica")
public class LocalidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localidad")
    private Long idLocalidad;

    @NotBlank(message = "El nombre de la localidad es obligatorio")
    @Size(min = 2, max = 500, message = "El nombre debe tener entre 2 y 500 caracteres")
    @Column(name = "nombre", unique = true, nullable = false, length = 500)
    private String nombre;
    
    @OneToMany(
        mappedBy = "localidad", 
        cascade = CascadeType.ALL, 
        fetch = FetchType.LAZY, 
        orphanRemoval = true
    )
    @Builder.Default
    @JsonManagedReference
    private List<AreaEntity> areas = new ArrayList<>();

    @Column(name = "estatus", nullable = false)
    @Builder.Default
    private Boolean estatus = true;

    /**
     * Agrega un área a la localidad manteniendo la relación bidireccional.
     * @param area El área a agregar
     */
    public void addArea(AreaEntity area) {
        if (areas == null) {
            areas = new ArrayList<>();
        }
        areas.add(area);
        area.setLocalidad(this);
    }

    /**
     * Remueve un área de la localidad.
     * @param area El área a remover
     */
    public void removeArea(AreaEntity area) {
        if (areas != null) {
            areas.remove(area);
            area.setLocalidad(null);
        }
    }

    /**
     * Setter personalizado que mantiene la sincronización bidireccional.
     * @param areas Lista de áreas a establecer
     */
    public void setAreas(List<AreaEntity> areas) {
        if (this.areas == null) {
            this.areas = new ArrayList<>();
        }
        this.areas.clear();
        if (areas != null) {
            areas.forEach(this::addArea);
        }
    }

}
