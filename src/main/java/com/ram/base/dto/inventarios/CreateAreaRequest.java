package com.ram.base.dto.inventarios;

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
 * Request para crear un área individual
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para crear un área")
public class CreateAreaRequest {

    @NotBlank(message = "El nombre del área es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotNull(message = "La localidad es obligatoria")
    @Positive(message = "El ID de la localidad debe ser un número positivo")
    private Long idLocalidad;

    @Schema(description = "Estado activo/inactivo", example = "true")
    @Builder.Default
    private Boolean estatus = true;
}
