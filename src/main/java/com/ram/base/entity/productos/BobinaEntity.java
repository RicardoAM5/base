package com.ram.base.entity.productos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una bobina comprada a proveedores.
 * Catálogo de bobinas para su posterior uso en producción.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bobina", indexes = {
    @Index(name = "idx_bobina_codigo", columnList = "codigo_proveedor"),
    @Index(name = "idx_bobina_proveedor", columnList = "proveedor_id")
})
@Schema(description = "Entidad que representa una bobina del catálogo")
public class BobinaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bobina")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id_bobina;

    @NotBlank(message = "El código del proveedor es obligatorio")
    @Size(max = 100, message = "El código del proveedor no puede exceder 100 caracteres")
    @Column(name = "codigo_proveedor", unique = true, nullable = false, length = 100)
    private String codigoProveedor;

    @NotNull(message = "El ancho es obligatorio")
    @Positive(message = "El ancho debe ser mayor a 0")
    @Column(name = "ancho", nullable = false)
    private Double ancho;

    @NotNull(message = "El gramaje es obligatorio")
    @Positive(message = "El gramaje debe ser mayor a 0")
    @Column(name = "gramaje", nullable = false)
    private Double gramaje;

    @Size(max = 50, message = "El calibre no puede exceder 50 caracteres")
    @Column(name = "calibre", length = 50)
    private String calibre;

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor a 0")
    @Column(name = "peso", nullable = false)
    private Double peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", nullable = false)
    @Schema(implementation = Long.class, description = "ID del tipo")
    private TipoEntity tipoEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_clase", nullable = false)
    @Schema(implementation = Long.class, description = "ID de la clase")
    private ClaseEntity claseEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_molino", nullable = false)
    @Schema(implementation = Long.class, description = "ID del molino")
    private MolinoEntity molinoEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grado", nullable = false)
    @Schema(implementation = Long.class, description = "ID del grado")
    private GradoEntity gradoEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    @Schema(implementation = Long.class, description = "ID del proveedor")
    private ProveedorEntity proveedor;
}

