package com.ram.base.dto.inventarios;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferencia de datos de Área
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de un área")
public class AreaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idArea;

    @NotBlank(message = "El nombre del área es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idLocalidad;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String nombreLocalidad;

    @Builder.Default
    private Boolean estatus = true;
}
