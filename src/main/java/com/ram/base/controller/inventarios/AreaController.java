package com.ram.base.controller.inventarios;

import com.ram.base.dto.inventarios.AreaDTO;
import com.ram.base.dto.inventarios.CreateAreaRequest;
import com.ram.base.service.inventarios.AreaService;
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
 * Controller REST para gestión de Áreas
 */
@Slf4j
@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Áreas", description = "API para gestión de áreas dentro de localidades")
public class AreaController {

    private final AreaService areaService;

    @PostMapping
    @Operation(
            summary = "Crear nueva área",
            description = "Crea una nueva área. DEBE estar asociada a una localidad existente (obligatorio)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Área creada exitosamente",
                    content = @Content(schema = @Schema(implementation = AreaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o área duplicada en la localidad"),
            @ApiResponse(responseCode = "404", description = "Localidad no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AreaDTO> create(
            @Parameter(description = "Datos del área a crear", required = true)
            @Valid @RequestBody CreateAreaRequest request) {
        
        log.info("POST /areas - Creando área: {} para localidad ID: {}", 
                request.getNombre(), request.getIdLocalidad());
        AreaDTO created = areaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar área",
            description = "Actualiza los datos de un área existente. No se puede cambiar la localidad."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AreaDTO> update(
            @Parameter(description = "ID del área", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados", required = true)
            @Valid @RequestBody AreaDTO areaDTO) {
        
        log.info("PUT /areas/{} - Actualizando área", id);
        AreaDTO updated = areaService.update(id, areaDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener área por ID",
            description = "Retorna los detalles de un área específica incluyendo datos de su localidad"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área encontrada"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AreaDTO> getById(
            @Parameter(description = "ID del área", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("GET /areas/{} - Obteniendo área", id);
        AreaDTO area = areaService.getById(id);
        return ResponseEntity.ok(area);
    }

    @GetMapping
    @Operation(
            summary = "Obtener todas las áreas",
            description = "Retorna una lista de todas las áreas sin paginación"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<AreaDTO>> getAll() {
        log.info("GET /areas - Obteniendo todas las áreas");
        List<AreaDTO> areas = areaService.getAll();
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/paginated")
    @Operation(
            summary = "Obtener áreas paginadas",
            description = "Retorna áreas con paginación, ordenamiento y filtros"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Page<AreaDTO>> getAllPaginated(
            @Parameter(description = "Configuración de paginación")
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        
        log.info("GET /areas/paginated - Página: {}, Tamaño: {}", 
                pageable.getPageNumber(), pageable.getPageSize());
        Page<AreaDTO> page = areaService.getAllPaginated(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/localidad/{idLocalidad}")
    @Operation(
            summary = "Obtener áreas por localidad",
            description = "Retorna todas las áreas pertenecientes a una localidad específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Localidad no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<AreaDTO>> getByLocalidad(
            @Parameter(description = "ID de la localidad", required = true, example = "1")
            @PathVariable Long idLocalidad) {
        
        log.info("GET /areas/localidad/{} - Obteniendo áreas de la localidad", idLocalidad);
        List<AreaDTO> areas = areaService.getByLocalidad(idLocalidad);
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/activas")
    @Operation(
            summary = "Obtener áreas activas",
            description = "Retorna solo las áreas con estatus activo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<AreaDTO>> getActive() {
        log.info("GET /areas/activas - Obteniendo áreas activas");
        List<AreaDTO> activas = areaService.getActive();
        return ResponseEntity.ok(activas);
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar áreas por nombre",
            description = "Busca áreas cuyo nombre contenga el texto especificado (case insensitive)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda completada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<AreaDTO>> searchByNombre(
            @Parameter(description = "Texto a buscar en el nombre", required = true, example = "Almacén")
            @RequestParam String nombre) {
        
        log.info("GET /areas/buscar?nombre={}", nombre);
        List<AreaDTO> resultados = areaService.searchByNombre(nombre);
        return ResponseEntity.ok(resultados);
    }

    @PatchMapping("/{id}/activar")
    @Operation(
            summary = "Activar área",
            description = "Cambia el estatus del área a activo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área activada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AreaDTO> activate(
            @Parameter(description = "ID del área", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /areas/{}/activar", id);
        AreaDTO activated = areaService.activate(id);
        return ResponseEntity.ok(activated);
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(
            summary = "Desactivar área",
            description = "Cambia el estatus del área a inactivo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área desactivada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AreaDTO> deactivate(
            @Parameter(description = "ID del área", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /areas/{}/desactivar", id);
        AreaDTO deactivated = areaService.deactivate(id);
        return ResponseEntity.ok(deactivated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar área",
            description = "Elimina lógicamente un área (soft delete, la desactiva)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Área eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Área no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID del área", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("DELETE /areas/{}", id);
        areaService.delete(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Área eliminada exitosamente");
        response.put("id", id.toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localidad/{idLocalidad}/contar")
    @Operation(
            summary = "Contar áreas por localidad",
            description = "Retorna el número total de áreas en una localidad específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo completado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Map<String, Long>> countByLocalidad(
            @Parameter(description = "ID de la localidad", required = true, example = "1")
            @PathVariable Long idLocalidad) {
        
        log.info("GET /areas/localidad/{}/contar", idLocalidad);
        long count = areaService.countByLocalidad(idLocalidad);
        
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        response.put("idLocalidad", idLocalidad);
        
        return ResponseEntity.ok(response);
    }
}
