package com.ram.base.controller.generic;

import com.ram.base.service.generic.BaseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador base genérico que proporciona operaciones CRUD estándar.
 * @param <E> Tipo de entidad
 * @param <S> Tipo de servicio
 */
@Slf4j
public abstract class BaseControllerImpl<E, S extends BaseServiceImpl<E, Long>> implements BaseController<E, Long> {

    @Autowired
    protected S service;

    @GetMapping("/")
    @Operation(summary = "Obtener todas las entidades", description = "Retorna una lista de todas las entidades")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<E>> getAll() {
        try {
            log.debug("Obteniendo todas las entidades");
            List<E> entities = service.getAll();
            log.debug("Se encontraron {} entidades", entities.size());
            return ResponseEntity.ok(entities);
        } catch (Exception e) {
            log.error("Error al obtener todas las entidades: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener entidad por ID", description = "Retorna una entidad específica por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidad encontrada"),
        @ApiResponse(responseCode = "404", description = "Entidad no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<E> getById(
            @Parameter(description = "ID de la entidad", required = true, example = "1")
            @PathVariable Long id) {
        try {
            log.debug("Buscando entidad con ID: {}", id);
            E entity = service.getById(id);
            if (entity != null) {
                log.debug("Entidad encontrada con ID: {}", id);
                return ResponseEntity.ok(entity);
            } else {
                log.debug("Entidad no encontrada con ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error al buscar entidad con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/")
    @Operation(summary = "Crear nueva entidad", description = "Crea y guarda una nueva entidad en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Entidad creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Entidad a crear", required = true)
            @Valid @RequestBody E entity) {
        try {
            log.debug("Creando nueva entidad: {}", entity);
            E savedEntity = service.save(entity);
            log.info("Entidad creada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
        } catch (Exception e) {
            log.error("Error al crear entidad: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear la entidad");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar entidad", description = "Actualiza una entidad existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidad actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Entidad no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la entidad a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la entidad", required = true)
            @Valid @RequestBody E entity) {
        try {
            log.debug("Actualizando entidad con ID: {}", id);
            E updatedEntity = service.update(id, entity);
            log.info("Entidad con ID {} actualizada exitosamente", id);
            return ResponseEntity.ok(updatedEntity);
        } catch (IllegalArgumentException e) {
            log.warn("Entidad no encontrada con ID: {}", id);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Entidad no encontrada");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            log.error("Error al actualizar entidad con ID {}: {}", id, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar la entidad");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PatchMapping("/activate/{id}")
    @Operation(summary = "Activar entidad", description = "Activa una entidad cambiando su estado a activo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidad activada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Entidad no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> activate(
            @Parameter(description = "ID de la entidad a activar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            log.debug("Activando entidad con ID: {}", id);
            E activatedEntity = service.activate(id);
            log.info("Entidad con ID {} activada exitosamente", id);
            return ResponseEntity.ok(activatedEntity);
        } catch (IllegalArgumentException e) {
            log.warn("Entidad no encontrada con ID: {}", id);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Entidad no encontrada");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            log.error("Error al activar entidad con ID {}: {}", id, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al activar la entidad");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PatchMapping("/deactivate/{id}")
    @Operation(summary = "Desactivar entidad", description = "Desactiva una entidad cambiando su estado a inactivo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entidad desactivada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Entidad no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> deactivate(
            @Parameter(description = "ID de la entidad a desactivar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            log.debug("Desactivando entidad con ID: {}", id);
            E deactivatedEntity = service.deactivate(id);
            log.info("Entidad con ID {} desactivada exitosamente", id);
            return ResponseEntity.ok(deactivatedEntity);
        } catch (IllegalArgumentException e) {
            log.warn("Entidad no encontrada con ID: {}", id);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Entidad no encontrada");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            log.error("Error al desactivar entidad con ID {}: {}", id, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al desactivar la entidad");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}




