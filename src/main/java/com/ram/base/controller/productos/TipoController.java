package com.ram.base.controller.productos;

import com.ram.base.dto.productos.TipoDTO;
import com.ram.base.service.productos.TipoService;
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
 * Controller REST para gestión de Tipos
 */
@Slf4j
@RestController
@RequestMapping("/tipos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Tipos", description = "API para gestión de tipos de productos")
public class TipoController {

    private final TipoService tipoService;

    @PostMapping
    @Operation(
            summary = "Crear nuevo tipo",
            description = "Crea un nuevo tipo de producto"
    )
    public ResponseEntity<TipoDTO> create(
            @Parameter(description = "Datos del tipo a crear", required = true)
            @Valid @RequestBody TipoDTO tipoDTO) {
        
        log.info("POST /tipos - Creando tipo: {}", tipoDTO.getTipo());
        TipoDTO created = tipoService.create(tipoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar tipo",
            description = "Actualiza los datos de un tipo existente"
    )
    public ResponseEntity<TipoDTO> update(
            @Parameter(description = "ID del tipo", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados", required = true)
            @Valid @RequestBody TipoDTO tipoDTO) {
        
        log.info("PUT /tipos/{} - Actualizando tipo", id);
        TipoDTO updated = tipoService.update(id, tipoDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener tipo por ID",
            description = "Retorna los detalles de un tipo específico"
    )
    public ResponseEntity<TipoDTO> getById(
            @Parameter(description = "ID del tipo", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("GET /tipos/{} - Obteniendo tipo", id);
        TipoDTO tipo = tipoService.getById(id);
        return ResponseEntity.ok(tipo);
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los tipos",
            description = "Retorna una lista de todos los tipos sin paginación"
    )
    public ResponseEntity<List<TipoDTO>> getAll() {
        log.info("GET /tipos - Obteniendo todos los tipos");
        List<TipoDTO> tipos = tipoService.getAll();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/paginated")
    @Operation(
            summary = "Obtener tipos paginados",
            description = "Retorna tipos con paginación, ordenamiento y filtros"
    )
    public ResponseEntity<Page<TipoDTO>> getAllPaginated(
            @Parameter(description = "Configuración de paginación")
            @PageableDefault(size = 10, sort = "tipo", direction = Sort.Direction.ASC) Pageable pageable) {
        
        log.info("GET /tipos/paginated - Página: {}, Tamaño: {}", 
                pageable.getPageNumber(), pageable.getPageSize());
        Page<TipoDTO> page = tipoService.getAllPaginated(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/activos")
    @Operation(
            summary = "Obtener tipos activos",
            description = "Retorna solo los tipos con estatus activo"
    )
    public ResponseEntity<List<TipoDTO>> getActive() {
        log.info("GET /tipos/activos - Obteniendo tipos activos");
        List<TipoDTO> activos = tipoService.getActive();
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar tipos por nombre",
            description = "Busca tipos cuyo nombre contenga el texto especificado (case insensitive)"
    )
    public ResponseEntity<List<TipoDTO>> searchByTipo(
            @Parameter(description = "Texto a buscar en el nombre", required = true, example = "Tipo A")
            @RequestParam String tipo) {
        
        log.info("GET /tipos/buscar?tipo={}", tipo);
        List<TipoDTO> resultados = tipoService.searchByTipo(tipo);
        return ResponseEntity.ok(resultados);
    }

    @PatchMapping("/{id}/activar")
    @Operation(
            summary = "Activar tipo",
            description = "Cambia el estatus del tipo a activo"
    )
    public ResponseEntity<TipoDTO> activate(
            @Parameter(description = "ID del tipo", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /tipos/{}/activar", id);
        TipoDTO activated = tipoService.activate(id);
        return ResponseEntity.ok(activated);
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(
            summary = "Desactivar tipo",
            description = "Cambia el estatus del tipo a inactivo"
    )
    public ResponseEntity<TipoDTO> deactivate(
            @Parameter(description = "ID del tipo", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /tipos/{}/desactivar", id);
        TipoDTO deactivated = tipoService.deactivate(id);
        return ResponseEntity.ok(deactivated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar tipo",
            description = "Elimina lógicamente un tipo (soft delete, lo desactiva)"
    )
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID del tipo", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("DELETE /tipos/{}", id);
        tipoService.delete(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tipo eliminado exitosamente");
        response.put("id", id.toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/existe")
    @Operation(
            summary = "Verificar si existe tipo",
            description = "Verifica si existe un tipo con el nombre especificado"
    )
    public ResponseEntity<Map<String, Boolean>> existsByTipo(
            @Parameter(description = "Nombre a verificar", required = true, example = "Tipo A")
            @RequestParam String tipo) {
        
        log.info("GET /tipos/existe?tipo={}", tipo);
        boolean existe = tipoService.existsByTipo(tipo);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }
}
