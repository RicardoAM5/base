package com.ram.base.controller.productos;

import com.ram.base.dto.productos.ClaseDTO;
import com.ram.base.service.productos.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * Controller REST para gestión de Clases
 */
@Slf4j
@RestController
@RequestMapping("/clases")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Clases", description = "API para gestión de clases de productos")
public class ClaseController {

    private final ClaseService claseService;

    @PostMapping
    @Operation(
            summary = "Crear nueva clase",
            description = "Crea una nueva clase de producto"
    )
    public ResponseEntity<ClaseDTO> create(
            @Parameter(description = "Datos de la clase a crear", required = true)
            @Valid @RequestBody ClaseDTO claseDTO) {
        
        log.info("POST /clases - Creando clase: {}", claseDTO.getClase());
        ClaseDTO created = claseService.create(claseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar clase",
            description = "Actualiza los datos de una clase existente"
    )
    public ResponseEntity<ClaseDTO> update(
            @Parameter(description = "ID de la clase", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados", required = true)
            @Valid @RequestBody ClaseDTO claseDTO) {
        
        log.info("PUT /clases/{} - Actualizando clase", id);
        ClaseDTO updated = claseService.update(id, claseDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener clase por ID",
            description = "Retorna los detalles de una clase específica"
    )
    public ResponseEntity<ClaseDTO> getById(
            @Parameter(description = "ID de la clase", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("GET /clases/{} - Obteniendo clase", id);
        ClaseDTO clase = claseService.getById(id);
        return ResponseEntity.ok(clase);
    }

    @GetMapping
    @Operation(
            summary = "Obtener todas las clases",
            description = "Retorna una lista de todas las clases sin paginación"
    )
    public ResponseEntity<List<ClaseDTO>> getAll() {
        log.info("GET /clases - Obteniendo todas las clases");
        List<ClaseDTO> clases = claseService.getAll();
        return ResponseEntity.ok(clases);
    }

    @GetMapping("/paginated")
    @Operation(
            summary = "Obtener clases paginadas",
            description = "Retorna clases con paginación, ordenamiento y filtros"
    )
    public ResponseEntity<Page<ClaseDTO>> getAllPaginated(
            @Parameter(description = "Configuración de paginación")
            @PageableDefault(size = 10, sort = "clase", direction = Sort.Direction.ASC) Pageable pageable) {
        
        log.info("GET /clases/paginated - Página: {}, Tamaño: {}", 
                pageable.getPageNumber(), pageable.getPageSize());
        Page<ClaseDTO> page = claseService.getAllPaginated(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/activas")
    @Operation(
            summary = "Obtener clases activas",
            description = "Retorna solo las clases con estatus activo"
    )
    public ResponseEntity<List<ClaseDTO>> getActive() {
        log.info("GET /clases/activas - Obteniendo clases activas");
        List<ClaseDTO> activas = claseService.getActive();
        return ResponseEntity.ok(activas);
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar clases por nombre",
            description = "Busca clases cuyo nombre contenga el texto especificado (case insensitive)"
    )
    public ResponseEntity<List<ClaseDTO>> searchByClase(
            @Parameter(description = "Texto a buscar en el nombre", required = true, example = "Premium")
            @RequestParam String clase) {
        
        log.info("GET /clases/buscar?clase={}", clase);
        List<ClaseDTO> resultados = claseService.searchByClase(clase);
        return ResponseEntity.ok(resultados);
    }

    @PatchMapping("/{id}/activar")
    @Operation(
            summary = "Activar clase",
            description = "Cambia el estatus de la clase a activo"
    )
    public ResponseEntity<ClaseDTO> activate(
            @Parameter(description = "ID de la clase", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /clases/{}/activar", id);
        ClaseDTO activated = claseService.activate(id);
        return ResponseEntity.ok(activated);
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(
            summary = "Desactivar clase",
            description = "Cambia el estatus de la clase a inactivo"
    )
    public ResponseEntity<ClaseDTO> deactivate(
            @Parameter(description = "ID de la clase", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /clases/{}/desactivar", id);
        ClaseDTO deactivated = claseService.deactivate(id);
        return ResponseEntity.ok(deactivated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar clase",
            description = "Elimina lógicamente una clase (soft delete, la desactiva)"
    )
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID de la clase", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("DELETE /clases/{}", id);
        claseService.delete(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Clase eliminada exitosamente");
        response.put("id", id.toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/existe")
    @Operation(
            summary = "Verificar si existe clase",
            description = "Verifica si existe una clase con el nombre especificado"
    )
    public ResponseEntity<Map<String, Boolean>> existsByClase(
            @Parameter(description = "Nombre a verificar", required = true, example = "Premium")
            @RequestParam String clase) {
        
        log.info("GET /clases/existe?clase={}", clase);
        boolean existe = claseService.existsByClase(clase);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }
}
