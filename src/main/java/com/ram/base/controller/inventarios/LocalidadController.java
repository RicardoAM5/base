package com.ram.base.controller.inventarios;

import com.ram.base.dto.inventarios.LocalidadDTO;
import com.ram.base.service.inventarios.LocalidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para gestión de Localidades
 */
@Slf4j
@RestController
@RequestMapping("/localidades")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Localidades", description = "API para gestión de localidades geográficas")
public class LocalidadController {

    private final LocalidadService localidadService;

    @PostMapping
    @Operation(
            summary = "Crear nueva localidad",
            description = "Crea una nueva localidad. Puede incluir áreas asociadas o crearse sin áreas."
    )

    public ResponseEntity<LocalidadDTO> create(
            @Parameter(description = "Datos de la localidad a crear", required = true)
            @Valid @RequestBody LocalidadDTO localidadDTO) {
        
        log.info("POST /localidades - Creando localidad: {}", localidadDTO.getNombre());
        LocalidadDTO created = localidadService.create(localidadDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar localidad",
            description = "Actualiza los datos de una localidad existente"
    )

    public ResponseEntity<LocalidadDTO> update(
            @Parameter(description = "ID de la localidad", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados", required = true)
            @Valid @RequestBody LocalidadDTO localidadDTO) {
        
        log.info("PUT /localidades/{} - Actualizando localidad", id);
        LocalidadDTO updated = localidadService.update(id, localidadDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener localidad por ID",
            description = "Retorna los detalles de una localidad específica incluyendo sus áreas"
    )
    public ResponseEntity<LocalidadDTO> getById(
            @Parameter(description = "ID de la localidad", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("GET /localidades/{} - Obteniendo localidad", id);
        LocalidadDTO localidad = localidadService.getById(id);
        return ResponseEntity.ok(localidad);
    }

    @GetMapping
    @Operation(
            summary = "Obtener todas las localidades",
            description = "Retorna una lista de todas las localidades sin paginación"
    )
    public ResponseEntity<List<LocalidadDTO>> getAll() {
        log.info("GET /localidades - Obteniendo todas las localidades");
        List<LocalidadDTO> localidades = localidadService.getAll();
        return ResponseEntity.ok(localidades);
    }

    @GetMapping("/paginated")
    @Operation(
            summary = "Obtener localidades paginadas",
            description = "Retorna localidades con paginación, ordenamiento y filtros"
    )

    public ResponseEntity<Page<LocalidadDTO>> getAllPaginated(
            @Parameter(description = "Configuración de paginación")
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        
        log.info("GET /localidades/paginated - Página: {}, Tamaño: {}", 
                pageable.getPageNumber(), pageable.getPageSize());
        Page<LocalidadDTO> page = localidadService.getAllPaginated(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/activas")
    @Operation(
            summary = "Obtener localidades activas",
            description = "Retorna solo las localidades con estatus activo"
    )

    public ResponseEntity<List<LocalidadDTO>> getActive() {
        log.info("GET /localidades/activas - Obteniendo localidades activas");
        List<LocalidadDTO> activas = localidadService.getActive();
        return ResponseEntity.ok(activas);
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar localidades por nombre",
            description = "Busca localidades cuyo nombre contenga el texto especificado (case insensitive)"
    )

    public ResponseEntity<List<LocalidadDTO>> searchByNombre(
            @Parameter(description = "Texto a buscar en el nombre", required = true, example = "México")
            @RequestParam String nombre) {
        
        log.info("GET /localidades/buscar?nombre={}", nombre);
        List<LocalidadDTO> resultados = localidadService.searchByNombre(nombre);
        return ResponseEntity.ok(resultados);
    }

    @PatchMapping("/{id}/activar")
    @Operation(
            summary = "Activar localidad",
            description = "Cambia el estatus de la localidad a activo"
    )

    public ResponseEntity<LocalidadDTO> activate(
            @Parameter(description = "ID de la localidad", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /localidades/{}/activar", id);
        LocalidadDTO activated = localidadService.activate(id);
        return ResponseEntity.ok(activated);
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(
            summary = "Desactivar localidad",
            description = "Cambia el estatus de la localidad a inactivo"
    )
    public ResponseEntity<LocalidadDTO> deactivate(
            @Parameter(description = "ID de la localidad", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /localidades/{}/desactivar", id);
        LocalidadDTO deactivated = localidadService.deactivate(id);
        return ResponseEntity.ok(deactivated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar localidad",
            description = "Elimina lógicamente una localidad (soft delete, la desactiva)"
    )
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID de la localidad", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("DELETE /localidades/{}", id);
        localidadService.delete(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Localidad eliminada exitosamente");
        response.put("id", id.toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/existe")
    @Operation(
            summary = "Verificar si existe localidad",
            description = "Verifica si existe una localidad con el nombre especificado"
    )

    public ResponseEntity<Map<String, Boolean>> existsByNombre(
            @Parameter(description = "Nombre a verificar", required = true, example = "Ciudad de México")
            @RequestParam String nombre) {
        
        log.info("GET /localidades/existe?nombre={}", nombre);
        boolean existe = localidadService.existsByNombre(nombre);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }
}
