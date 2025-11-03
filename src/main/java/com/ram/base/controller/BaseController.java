package com.ram.base.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public interface BaseController <E , ID extends Serializable> {

    public ResponseEntity <?> getAll();
    public ResponseEntity<?> getById( @PathVariable  ID id);
    public ResponseEntity <?> save( @RequestBody E entity);
    public ResponseEntity <?> update( @PathVariable ID id,  @RequestBody E entity);
    public ResponseEntity <?> activate(@PathVariable ID id);
    public ResponseEntity <?> deactivate( @PathVariable ID id);

}
