package com.ram.base.service;

import java.util.List;

//Este es un servicio base generico que define las operaciones CRUD basicas
//para cualquier entidad. Utiliza genericos para permitir que diferentes tipos
//de entidades implementen este servicio sin necesidad de duplicar codigo.


public interface BaseService <E , ID > {

    public List <E> getAll() throws Exception ;
    public E getById(ID id) throws Exception ;
    public E save(E entity) throws Exception ;
    public E update(ID id, E entity) throws Exception;
    public E activate(ID id) throws Exception ;
    public E  deactivate (ID id) throws Exception ;

}
