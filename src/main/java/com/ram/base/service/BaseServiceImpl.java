package com.ram.base.service;

import com.ram.base.repository.BaseRepository;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<E, ID extends Serializable> implements BaseService<E, ID> {

    protected BaseRepository<E, ID> baseRepository;

    public BaseServiceImpl(BaseRepository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional
    public List<E> getAll() throws Exception {
        try {
            return baseRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E getById(ID id) throws Exception {
        try {
            return baseRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            return baseRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            if (baseRepository.existsById(id)) {
                return baseRepository.save(entity);
            } else {
                throw new Exception("Entity not found for update");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    @Transactional
    public E activate(ID id) throws Exception {
        try {
            E entity = baseRepository.findById(id).orElse(null);
            if (entity != null) {
                entity.getClass().getMethod("setEstatus", boolean.class).invoke(entity, true);
                return baseRepository.save(entity);
            } else {
                throw new Exception("Entity not found for activation");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E deactivate(ID id) throws Exception {
        try {
            E entity = baseRepository.findById(id).orElse(null);
            if (entity != null) {
                entity.getClass().getMethod("setEstatus", boolean.class).invoke(entity, false);
                return baseRepository.save(entity);
            } else {
                throw new Exception("Entity not found for deactivation");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
