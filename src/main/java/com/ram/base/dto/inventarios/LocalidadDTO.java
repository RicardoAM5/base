package com.ram.base.dto.inventarios;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para transferencia de datos de Localidad
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de una localidad")
public class LocalidadDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idLocalidad;

    @NotBlank(message = "El nombre de la localidad es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Valid
    @Schema(description = "Lista de áreas asociadas a la localidad")
    @Builder.Default
    private List<AreaDTO> areas = new ArrayList<>();

    @Builder.Default
    private Boolean estatus = true;
}
