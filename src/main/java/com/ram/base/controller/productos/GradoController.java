package com.ram.base.controller.productos;

import com.ram.base.dto.productos.GradoDTO;
import com.ram.base.service.productos.GradoService;
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
 * Controller REST para gestión de Grados
 */
@Slf4j
@RestController
@RequestMapping("/grados")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Grados", description = "API para gestión de grados de productos")
public class GradoController {

    private final GradoService gradoService;

    @PostMapping
    @Operation(
            summary = "Crear nuevo grado",
            description = "Crea un nuevo grado de producto"
    )
    public ResponseEntity<GradoDTO> create(
            @Parameter(description = "Datos del grado a crear", required = true)
            @Valid @RequestBody GradoDTO gradoDTO) {
        
        log.info("POST /grados - Creando grado: {}", gradoDTO.getGrado());
        GradoDTO created = gradoService.create(gradoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar grado",
            description = "Actualiza los datos de un grado existente"
    )
    public ResponseEntity<GradoDTO> update(
            @Parameter(description = "ID del grado", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados", required = true)
            @Valid @RequestBody GradoDTO gradoDTO) {
        
        log.info("PUT /grados/{} - Actualizando grado", id);
        GradoDTO updated = gradoService.update(id, gradoDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener grado por ID",
            description = "Retorna los detalles de un grado específico"
    )
    public ResponseEntity<GradoDTO> getById(
            @Parameter(description = "ID del grado", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("GET /grados/{} - Obteniendo grado", id);
        GradoDTO grado = gradoService.getById(id);
        return ResponseEntity.ok(grado);
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los grados",
            description = "Retorna una lista de todos los grados sin paginación"
    )
    public ResponseEntity<List<GradoDTO>> getAll() {
        log.info("GET /grados - Obteniendo todos los grados");
        List<GradoDTO> grados = gradoService.getAll();
        return ResponseEntity.ok(grados);
    }

    @GetMapping("/paginated")
    @Operation(
            summary = "Obtener grados paginados",
            description = "Retorna grados con paginación, ordenamiento y filtros"
    )
    public ResponseEntity<Page<GradoDTO>> getAllPaginated(
            @Parameter(description = "Configuración de paginación")
            @PageableDefault(size = 10, sort = "grado", direction = Sort.Direction.ASC) Pageable pageable) {
        
        log.info("GET /grados/paginated - Página: {}, Tamaño: {}", 
                pageable.getPageNumber(), pageable.getPageSize());
        Page<GradoDTO> page = gradoService.getAllPaginated(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/activos")
    @Operation(
            summary = "Obtener grados activos",
            description = "Retorna solo los grados con estatus activo"
    )
    public ResponseEntity<List<GradoDTO>> getActive() {
        log.info("GET /grados/activos - Obteniendo grados activos");
        List<GradoDTO> activos = gradoService.getActive();
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar grados por nombre",
            description = "Busca grados cuyo nombre contenga el texto especificado (case insensitive)"
    )
    public ResponseEntity<List<GradoDTO>> searchByGrado(
            @Parameter(description = "Texto a buscar en el nombre", required = true, example = "A")
            @RequestParam String grado) {
        
        log.info("GET /grados/buscar?grado={}", grado);
        List<GradoDTO> resultados = gradoService.searchByGrado(grado);
        return ResponseEntity.ok(resultados);
    }

    @PatchMapping("/{id}/activar")
    @Operation(
            summary = "Activar grado",
            description = "Cambia el estatus del grado a activo"
    )
    public ResponseEntity<GradoDTO> activate(
            @Parameter(description = "ID del grado", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /grados/{}/activar", id);
        GradoDTO activated = gradoService.activate(id);
        return ResponseEntity.ok(activated);
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(
            summary = "Desactivar grado",
            description = "Cambia el estatus del grado a inactivo"
    )
    public ResponseEntity<GradoDTO> deactivate(
            @Parameter(description = "ID del grado", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("PATCH /grados/{}/desactivar", id);
        GradoDTO deactivated = gradoService.deactivate(id);
        return ResponseEntity.ok(deactivated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar grado",
            description = "Elimina lógicamente un grado (soft delete, lo desactiva)"
    )
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID del grado", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("DELETE /grados/{}", id);
        gradoService.delete(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Grado eliminado exitosamente");
        response.put("id", id.toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/existe")
    @Operation(
            summary = "Verificar si existe grado",
            description = "Verifica si existe un grado con el nombre especificado"
    )
    public ResponseEntity<Map<String, Boolean>> existsByGrado(
            @Parameter(description = "Nombre a verificar", required = true, example = "A")
            @RequestParam String grado) {
        
        log.info("GET /grados/existe?grado={}", grado);
        boolean existe = gradoService.existsByGrado(grado);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }
}
