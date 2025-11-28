package com.ram.base.controller.productos;

import com.ram.base.dto.productos.MolinoDTO;
import com.ram.base.service.productos.MolinoService;
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
 * Controller REST para gestión de Molinos
 */
@Slf4j
@RestController
@RequestMapping("/molinos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Molinos", description = "API para gestión de molinos")
public class MolinoController {

    private final MolinoService molinoService;

    @PostMapping
    @Operation(
            summary = "Crear nuevo molino",
            description = "Crea un nuevo molino"
    )
    public ResponseEntity<MolinoDTO> create(
            @Parameter(description = "Datos del molino a crear", required = true)
            @Valid @RequestBody MolinoDTO molinoDTO) {
        
        log.info("POST /molinos - Creando molino: {}", molinoDTO.getMolino());
        MolinoDTO created = molinoService.create(molinoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar molino",
            description = "Actualiza los datos de un molino existente"
    )
    public ResponseEntity<MolinoDTO> update(
            @Parameter(description = "ID del molino", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados", required = true)
            @Valid @RequestBody MolinoDTO molinoDTO) {
        
        log.info("PUT /molinos/{} - Actualizando molino", id);
        MolinoDTO updated = molinoService.update(id, molinoDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener molino por ID",
            description = "Retorna los detalles de un molino específico"
    )
    public ResponseEntity<MolinoDTO> getById(
            @Parameter(description = "ID del molino", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("GET /molinos/{} - Obteniendo molino", id);
        MolinoDTO molino = molinoService.getById(id);
        return ResponseEntity.ok(molino);
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los molinos",
            description = "Retorna una lista de todos los molinos sin paginación"
    )
    public ResponseEntity<List<MolinoDTO>> getAll() {
        log.info("GET /molinos - Obteniendo todos los molinos");
        List<MolinoDTO> molinos = molinoService.getAll();
        return ResponseEntity.ok(molinos);
    }

    @GetMapping("/paginated")
    @Operation(
            summary = "Obtener molinos paginados",
            description = "Retorna molinos con paginación, ordenamiento y filtros"
    )
    public ResponseEntity<Page<MolinoDTO>> getAllPaginated(
            @Parameter(description = "Configuración de paginación")
            @PageableDefault(size = 10, sort = "molino", direction = Sort.Direction.ASC) Pageable pageable) {
        
        log.info("GET /molinos/paginated - Página: {}, Tamaño: {}", 
                pageable.getPageNumber(), pageable.getPageSize());
        Page<MolinoDTO> page = molinoService.getAllPaginated(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/activos")
    @Operation(
            summary = "Obtener molinos activos",
            description = "Retorna solo los molinos con estatus activo"
    )
    public ResponseEntity<List<MolinoDTO>> getActive() {
        log.info("GET /molinos/activos - Obteniendo molinos activos");
        List<MolinoDTO> activos = molinoService.getActive();
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar molinos por nombre",
            description = "Busca molinos cuyo nombre contenga el texto especificado (case insensitive)"
    )
    public ResponseEntity<List<MolinoDTO>> searchByMolino(
            @Parameter(description = "Texto a buscar en el nombre", required = true, example = "Central")
            @RequestParam String molino) {
        
        log.info("GET /molinos/buscar?molino={}", molino);
        List<MolinoDTO> resultados = molinoService.searchByMolino(molino);
        return ResponseEntity.ok(resultados);
    }

    @PatchMapping("/{id}/activar")
    @Operation(
            summary = "Activar molino",
            description = "Cambia el estatus del molino a activo"
    )
    public ResponseEntity<MolinoDTO> activate(
            @Parameter(description = "ID del molino", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /molinos/{}/activar", id);
        MolinoDTO activated = molinoService.activate(id);
        return ResponseEntity.ok(activated);
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(
            summary = "Desactivar molino",
            description = "Cambia el estatus del molino a inactivo"
    )
    public ResponseEntity<MolinoDTO> deactivate(
            @Parameter(description = "ID del molino", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /molinos/{}/desactivar", id);
        MolinoDTO deactivated = molinoService.deactivate(id);
        return ResponseEntity.ok(deactivated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar molino",
            description = "Elimina lógicamente un molino (soft delete, lo desactiva)"
    )
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID del molino", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("DELETE /molinos/{}", id);
        molinoService.delete(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Molino eliminado exitosamente");
        response.put("id", id.toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/existe")
    @Operation(
            summary = "Verificar si existe molino",
            description = "Verifica si existe un molino con el nombre especificado"
    )
    public ResponseEntity<Map<String, Boolean>> existsByMolino(
            @Parameter(description = "Nombre a verificar", required = true, example = "Molino Central")
            @RequestParam String molino) {
        
        log.info("GET /molinos/existe?molino={}", molino);
        boolean existe = molinoService.existsByMolino(molino);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }
}
