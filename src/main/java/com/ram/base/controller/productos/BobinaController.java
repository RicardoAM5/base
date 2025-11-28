package com.ram.base.controller.productos;

import com.ram.base.dto.productos.BobinaDTO;
import com.ram.base.service.productos.BobinaService;
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
 * Controller REST para gestión de Bobinas
 */
@Slf4j
@RestController
@RequestMapping("/bobinas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Bobinas", description = "API para gestión de bobinas (catálogo de bobinas compradas a proveedores)")
public class BobinaController {

    private final BobinaService bobinaService;

    @PostMapping
    @Operation(
            summary = "Crear nueva bobina",
            description = "Crea una nueva bobina en el catálogo"
    )
    public ResponseEntity<BobinaDTO> create(
            @Parameter(description = "Datos de la bobina a crear", required = true)
            @Valid @RequestBody BobinaDTO bobinaDTO) {
        
        log.info("POST /bobinas - Creando bobina: {}", bobinaDTO.getCodigoProveedor());
        BobinaDTO created = bobinaService.create(bobinaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar bobina",
            description = "Actualiza los datos de una bobina existente"
    )
    public ResponseEntity<BobinaDTO> update(
            @Parameter(description = "ID de la bobina", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados", required = true)
            @Valid @RequestBody BobinaDTO bobinaDTO) {
        
        log.info("PUT /bobinas/{} - Actualizando bobina", id);
        BobinaDTO updated = bobinaService.update(id, bobinaDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener bobina por ID",
            description = "Retorna los detalles de una bobina específica"
    )
    public ResponseEntity<BobinaDTO> getById(
            @Parameter(description = "ID de la bobina", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("GET /bobinas/{} - Obteniendo bobina", id);
        BobinaDTO bobina = bobinaService.getById(id);
        return ResponseEntity.ok(bobina);
    }

    @GetMapping
    @Operation(
            summary = "Obtener todas las bobinas",
            description = "Retorna una lista de todas las bobinas sin paginación"
    )
    public ResponseEntity<List<BobinaDTO>> getAll() {
        log.info("GET /bobinas - Obteniendo todas las bobinas");
        List<BobinaDTO> bobinas = bobinaService.getAll();
        return ResponseEntity.ok(bobinas);
    }

    @GetMapping("/paginated")
    @Operation(
            summary = "Obtener bobinas paginadas",
            description = "Retorna bobinas con paginación, ordenamiento y filtros"
    )
    public ResponseEntity<Page<BobinaDTO>> getAllPaginated(
            @Parameter(description = "Configuración de paginación")
            @PageableDefault(size = 10, sort = "codigoProveedor", direction = Sort.Direction.ASC) Pageable pageable) {
        
        log.info("GET /bobinas/paginated - Página: {}, Tamaño: {}", 
                pageable.getPageNumber(), pageable.getPageSize());
        Page<BobinaDTO> page = bobinaService.getAllPaginated(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar bobinas por código de proveedor",
            description = "Busca bobinas cuyo código contenga el texto especificado (case insensitive)"
    )
    public ResponseEntity<List<BobinaDTO>> searchByCodigoProveedor(
            @Parameter(description = "Texto a buscar en el código", required = true, example = "BOB-001")
            @RequestParam String codigo) {
        
        log.info("GET /bobinas/buscar?codigo={}", codigo);
        List<BobinaDTO> resultados = bobinaService.searchByCodigoProveedor(codigo);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/proveedor/{idProveedor}")
    @Operation(
            summary = "Obtener bobinas por proveedor",
            description = "Retorna todas las bobinas de un proveedor específico"
    )
    public ResponseEntity<List<BobinaDTO>> getByProveedor(
            @Parameter(description = "ID del proveedor", required = true, example = "1")
            @PathVariable Long idProveedor) {
        
        log.info("GET /bobinas/proveedor/{}", idProveedor);
        List<BobinaDTO> bobinas = bobinaService.getByProveedor(idProveedor);
        return ResponseEntity.ok(bobinas);
    }

    @GetMapping("/tipo/{idTipo}")
    @Operation(
            summary = "Obtener bobinas por tipo",
            description = "Retorna todas las bobinas de un tipo específico"
    )
    public ResponseEntity<List<BobinaDTO>> getByTipo(
            @Parameter(description = "ID del tipo", required = true, example = "1")
            @PathVariable Long idTipo) {
        
        log.info("GET /bobinas/tipo/{}", idTipo);
        List<BobinaDTO> bobinas = bobinaService.getByTipo(idTipo);
        return ResponseEntity.ok(bobinas);
    }

    @GetMapping("/clase/{idClase}")
    @Operation(
            summary = "Obtener bobinas por clase",
            description = "Retorna todas las bobinas de una clase específica"
    )
    public ResponseEntity<List<BobinaDTO>> getByClase(
            @Parameter(description = "ID de la clase", required = true, example = "1")
            @PathVariable Long idClase) {
        
        log.info("GET /bobinas/clase/{}", idClase);
        List<BobinaDTO> bobinas = bobinaService.getByClase(idClase);
        return ResponseEntity.ok(bobinas);
    }

    @GetMapping("/molino/{idMolino}")
    @Operation(
            summary = "Obtener bobinas por molino",
            description = "Retorna todas las bobinas de un molino específico"
    )
    public ResponseEntity<List<BobinaDTO>> getByMolino(
            @Parameter(description = "ID del molino", required = true, example = "1")
            @PathVariable Long idMolino) {
        
        log.info("GET /bobinas/molino/{}", idMolino);
        List<BobinaDTO> bobinas = bobinaService.getByMolino(idMolino);
        return ResponseEntity.ok(bobinas);
    }

    @GetMapping("/grado/{idGrado}")
    @Operation(
            summary = "Obtener bobinas por grado",
            description = "Retorna todas las bobinas de un grado específico"
    )
    public ResponseEntity<List<BobinaDTO>> getByGrado(
            @Parameter(description = "ID del grado", required = true, example = "1")
            @PathVariable Long idGrado) {
        
        log.info("GET /bobinas/grado/{}", idGrado);
        List<BobinaDTO> bobinas = bobinaService.getByGrado(idGrado);
        return ResponseEntity.ok(bobinas);
    }

    @GetMapping("/ancho")
    @Operation(
            summary = "Buscar bobinas por rango de ancho",
            description = "Retorna bobinas cuyo ancho esté dentro del rango especificado"
    )
    public ResponseEntity<List<BobinaDTO>> getByAnchoRange(
            @Parameter(description = "Ancho mínimo", required = true, example = "1.0")
            @RequestParam Double anchoMin,
            @Parameter(description = "Ancho máximo", required = true, example = "2.0")
            @RequestParam Double anchoMax) {
        
        log.info("GET /bobinas/ancho?anchoMin={}&anchoMax={}", anchoMin, anchoMax);
        List<BobinaDTO> bobinas = bobinaService.getByAnchoRange(anchoMin, anchoMax);
        return ResponseEntity.ok(bobinas);
    }

    @GetMapping("/gramaje")
    @Operation(
            summary = "Buscar bobinas por rango de gramaje",
            description = "Retorna bobinas cuyo gramaje esté dentro del rango especificado"
    )
    public ResponseEntity<List<BobinaDTO>> getByGramajeRange(
            @Parameter(description = "Gramaje mínimo", required = true, example = "50.0")
            @RequestParam Double gramajeMin,
            @Parameter(description = "Gramaje máximo", required = true, example = "100.0")
            @RequestParam Double gramajeMax) {
        
        log.info("GET /bobinas/gramaje?gramajeMin={}&gramajeMax={}", gramajeMin, gramajeMax);
        List<BobinaDTO> bobinas = bobinaService.getByGramajeRange(gramajeMin, gramajeMax);
        return ResponseEntity.ok(bobinas);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar bobina",
            description = "Elimina una bobina del sistema"
    )
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID de la bobina", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("DELETE /bobinas/{}", id);
        bobinaService.delete(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Bobina eliminada exitosamente");
        response.put("id", id.toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/existe")
    @Operation(
            summary = "Verificar si existe bobina",
            description = "Verifica si existe una bobina con el código de proveedor especificado"
    )
    public ResponseEntity<Map<String, Boolean>> existsByCodigoProveedor(
            @Parameter(description = "Código a verificar", required = true, example = "BOB-001-2024")
            @RequestParam String codigo) {
        
        log.info("GET /bobinas/existe?codigo={}", codigo);
        boolean existe = bobinaService.existsByCodigoProveedor(codigo);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }
}
