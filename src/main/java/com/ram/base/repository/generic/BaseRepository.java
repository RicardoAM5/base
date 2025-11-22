package com.ram.base.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


//Repositorio base generico que extiende JpaRepository para proporcionar
//operaciones CRUD basicas para cualquier entidad. Utiliza genericos para permitir
//que diferentes tipos de entidades implementen este repositorio sin necesidad
//de duplicar codigo.


import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository <E, ID extends Serializable> extends JpaRepository<E, ID> {
}
