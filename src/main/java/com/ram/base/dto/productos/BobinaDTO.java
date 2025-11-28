package com.ram.base.dto.productos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferencia de datos de Bobina
 * Representa el catálogo de bobinas compradas a proveedores
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de una bobina")
public class BobinaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "ID único de la bobina", example = "1")
    private Long idBobina;

    @NotBlank(message = "El código del proveedor es obligatorio")
    @Size(max = 100, message = "El código del proveedor no puede exceder 100 caracteres")
    @Schema(description = "Código asignado por el proveedor a la bobina", example = "BOB-001-2024")
    private String codigoProveedor;

    @NotNull(message = "El ancho es obligatorio")
    @Positive(message = "El ancho debe ser mayor a 0")
    @Schema(description = "Ancho del material en metros", example = "1.5")
    private Double ancho;

    @NotNull(message = "El gramaje es obligatorio")
    @Positive(message = "El gramaje debe ser mayor a 0")
    @Schema(description = "Gramaje del material", example = "80.0")
    private Double gramaje;

    @Size(max = 50, message = "El calibre no puede exceder 50 caracteres")
    @Schema(description = "Calibre del material", example = "12")
    private String calibre;

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor a 0")
    @Schema(description = "Peso en kilogramos", example = "500.0")
    private Double peso;

    @NotNull(message = "El ID del tipo es obligatorio")
    @Schema(description = "ID del tipo de bobina", example = "1")
    private Long idTipo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Nombre del tipo")
    private String nombreTipo;

    @NotNull(message = "El ID de la clase es obligatorio")
    @Schema(description = "ID de la clase de bobina", example = "1")
    private Long idClase;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Nombre de la clase")
    private String nombreClase;

    @NotNull(message = "El ID del molino es obligatorio")
    @Schema(description = "ID del molino", example = "1")
    private Long idMolino;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Nombre del molino")
    private String nombreMolino;

    @NotNull(message = "El ID del grado es obligatorio")
    @Schema(description = "ID del grado", example = "1")
    private Long idGrado;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Nombre del grado")
    private String nombreGrado;

    @NotNull(message = "El ID del proveedor es obligatorio")
    @Schema(description = "ID del proveedor", example = "1")
    private Long idProveedor;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Nombre comercial del proveedor")
    private String nombreProveedor;
}
