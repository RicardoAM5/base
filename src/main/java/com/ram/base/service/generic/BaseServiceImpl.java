package com.ram.base.service.generic;

import com.ram.base.repository.generic.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Servicio base genérico que proporciona implementación de operaciones CRUD comunes.
 * @param <E> Tipo de entidad
 * @param <ID> Tipo del identificador de la entidad
 */
@Slf4j
public abstract class BaseServiceImpl<E, ID extends Serializable> implements BaseService<E, ID> {

    protected final BaseRepository<E, ID> baseRepository;

    protected BaseServiceImpl(BaseRepository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> getAll() throws Exception {
        try {
            log.debug("Obteniendo todas las entidades de tipo {}", getEntityClass().getSimpleName());
            List<E> entities = baseRepository.findAll();
            log.debug("Se encontraron {} entidades", entities.size());
            return entities;
        } catch (DataAccessException e) {
            log.error("Error de acceso a datos al obtener todas las entidades: {}", e.getMessage(), e);
            throw new Exception("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener todas las entidades: {}", e.getMessage(), e);
            throw new Exception("Error inesperado al obtener las entidades: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public E getById(ID id) throws Exception {
        try {
            if (id == null) {
                throw new IllegalArgumentException("El ID no puede ser nulo");
            }
            log.debug("Buscando entidad con ID: {}", id);
            return baseRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("No se encontró la entidad con ID: %s", id)));
        } catch (DataAccessException e) {
            log.error("Error de acceso a datos al buscar entidad con ID {}: {}", id, e.getMessage(), e);
            throw new Exception("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.warn("Entidad no encontrada con ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al buscar entidad con ID {}: {}", id, e.getMessage(), e);
            throw new Exception("Error inesperado al buscar la entidad: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            if (entity == null) {
                throw new IllegalArgumentException("La entidad no puede ser nula");
            }
            log.debug("Guardando nueva entidad: {}", entity);
            E savedEntity = baseRepository.save(entity);
            log.info("Entidad guardada exitosamente con ID: {}", getEntityId(savedEntity));
            return savedEntity;
        } catch (DataAccessException e) {
            log.error("Error de acceso a datos al guardar entidad: {}", e.getMessage(), e);
            throw new Exception("Error al guardar en la base de datos: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al guardar entidad: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al guardar entidad: {}", e.getMessage(), e);
            throw new Exception("Error inesperado al guardar la entidad: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            if (id == null) {
                throw new IllegalArgumentException("El ID no puede ser nulo");
            }
            if (entity == null) {
                throw new IllegalArgumentException("La entidad no puede ser nula");
            }
            
            log.debug("Actualizando entidad con ID: {}", id);
            
            if (!baseRepository.existsById(id)) {
                throw new IllegalArgumentException(
                        String.format("No se encontró la entidad con ID: %s para actualizar", id));
            }
            
            E updatedEntity = baseRepository.save(entity);
            log.info("Entidad con ID {} actualizada exitosamente", id);
            return updatedEntity;
        } catch (DataAccessException e) {
            log.error("Error de acceso a datos al actualizar entidad con ID {}: {}", id, e.getMessage(), e);
            throw new Exception("Error al actualizar en la base de datos: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.warn("Error de validación al actualizar entidad con ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar entidad con ID {}: {}", id, e.getMessage(), e);
            throw new Exception("Error inesperado al actualizar la entidad: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public E activate(ID id) throws Exception {
        try {
            if (id == null) {
                throw new IllegalArgumentException("El ID no puede ser nulo");
            }
            
            log.debug("Activando entidad con ID: {}", id);
            
            E entity = baseRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("No se encontró la entidad con ID: %s para activar", id)));
            
            setEstatus(entity, true);
            E activatedEntity = baseRepository.save(entity);
            log.info("Entidad con ID {} activada exitosamente", id);
            return activatedEntity;
        } catch (DataAccessException e) {
            log.error("Error de acceso a datos al activar entidad con ID {}: {}", id, e.getMessage(), e);
            throw new Exception("Error al activar en la base de datos: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.warn("Error al activar entidad con ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al activar entidad con ID {}: {}", id, e.getMessage(), e);
            throw new Exception("Error inesperado al activar la entidad: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public E deactivate(ID id) throws Exception {
        try {
            if (id == null) {
                throw new IllegalArgumentException("El ID no puede ser nulo");
            }
            
            log.debug("Desactivando entidad con ID: {}", id);
            
            E entity = baseRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("No se encontró la entidad con ID: %s para desactivar", id)));
            
            setEstatus(entity, false);
            E deactivatedEntity = baseRepository.save(entity);
            log.info("Entidad con ID {} desactivada exitosamente", id);
            return deactivatedEntity;
        } catch (DataAccessException e) {
            log.error("Error de acceso a datos al desactivar entidad con ID {}: {}", id, e.getMessage(), e);
            throw new Exception("Error al desactivar en la base de datos: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.warn("Error al desactivar entidad con ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al desactivar entidad con ID {}: {}", id, e.getMessage(), e);
            throw new Exception("Error inesperado al desactivar la entidad: " + e.getMessage(), e);
        }
    }

    /**
     * Establece el estado de una entidad usando reflexión.
     * @param entity Entidad a modificar
     * @param estatus Nuevo estado
     * @throws Exception Si hay error al establecer el estado
     */
    protected void setEstatus(E entity, Boolean estatus) throws Exception {
        try {
            entity.getClass().getMethod("setEstatus", Boolean.class).invoke(entity, estatus);
        } catch (NoSuchMethodException e) {
            log.error("La entidad {} no tiene el método setEstatus(Boolean)", entity.getClass().getSimpleName());
            throw new Exception("La entidad no soporta el cambio de estado", e);
        } catch (Exception e) {
            log.error("Error al establecer el estado de la entidad: {}", e.getMessage(), e);
            throw new Exception("Error al cambiar el estado de la entidad", e);
        }
    }

    /**
     * Obtiene el ID de una entidad usando reflexión.
     * @param entity Entidad
     * @return ID de la entidad
     */
    protected Object getEntityId(E entity) {
        try {
            return entity.getClass().getMethod("getId" + getEntityClass().getSimpleName().replace("Entity", "")).invoke(entity);
        } catch (Exception e) {
            return "unknown";
        }
    }

    /**
     * Obtiene la clase de la entidad.
     * @return Clase de la entidad
     */
    @SuppressWarnings("unchecked")
    protected Class<E> getEntityClass() {
        return (Class<E>) ((java.lang.reflect.ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
