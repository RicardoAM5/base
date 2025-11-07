package com.ram.base.controller;

import com.ram.base.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;


public abstract class BaseControllerImpl<E, S extends BaseServiceImpl <E, Long >>implements BaseController<E, Long> {

    @Autowired
    protected S service;


    @GetMapping("/")
    public ResponseEntity<List<E>> getAll() {
        try {
            List<E> entities = service.getAll();
            return ResponseEntity.ok(entities);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<E> getById(Long id) {
        try {
            E entity = service.getById(id);
            if (entity != null) {
                return ResponseEntity.ok(entity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<E> save(E entity) {
        try {
            E savedEntity = service.save(entity);
            return ResponseEntity.ok(savedEntity);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<E> update(Long id, E entity) {
        try {
            E updatedEntity = service.update(id, entity);
            return ResponseEntity.ok(updatedEntity);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<E> activate(Long id) {
        try {
            E activatedEntity = service.activate(id);
            return ResponseEntity.ok(activatedEntity);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<E> deactivate(Long id) {
        try {
            E deactivatedEntity = service.deactivate(id);
            return ResponseEntity.ok(deactivatedEntity);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}




