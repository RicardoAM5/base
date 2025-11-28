package com.ram.base.entity.inventarios;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entidad que representa un área dentro de una localidad.
 * Cada área pertenece a una única localidad.
 */
@Entity
@Table(name = "area", 
    indexes = {
        @Index(name = "idx_area_nombre", columnList = "nombre"),
        @Index(name = "idx_area_localidad", columnList = "id_localidad"),
        @Index(name = "idx_area_estatus", columnList = "estatus")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_area_nombre_localidad", columnNames = {"nombre", "id_localidad"})
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idArea;

    @NotBlank(message = "El nombre del área es obligatorio")
    @Size(min = 2, max = 500, message = "El nombre debe tener entre 2 y 500 caracteres")
    @Column(name = "nombre", nullable = false, length = 500)
    private String nombre;

    @NotNull(message = "La localidad es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "id_localidad", 
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_area_localidad")
    )
    @JsonBackReference
    @ToString.Exclude
    private LocalidadEntity localidad;

    @Column(name = "estatus", nullable = false)
    @Builder.Default
    private Boolean estatus = true;


    //Esto sirve para asegurar que el estatus tenga un valor por defecto al crear el registro
    @PrePersist
    protected void onCreate() {
        if (estatus == null) {
            estatus = true;
        }
    }

    // Sobrescribir equals y hashCode basados en idArea para correcta comparación de entidades
    // Esto es importante para evitar problemas al usar colecciones y al comparar entidades
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AreaEntity)) return false;
        AreaEntity that = (AreaEntity) o;
        return idArea != null && idArea.equals(that.idArea);
    }

    //Por esto se usa getClass().hashCode() para evitar problemas con proxies de Hibernate
    // Es una práctica recomendada en entidades JPA
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
