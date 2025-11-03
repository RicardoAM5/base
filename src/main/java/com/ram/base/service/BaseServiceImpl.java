package com.ram.base.service;


import com.ram.base.repository.BaseRepository;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<E, ID extends Serializable> implements BaseService<E,ID> {

    protected BaseRepository<E,ID> baseRepository;

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




}
