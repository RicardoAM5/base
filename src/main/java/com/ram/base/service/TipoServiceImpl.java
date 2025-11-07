package com.ram.base.service;

import com.ram.base.entity.TipoEntity;
import com.ram.base.repository.BaseRepository;
import com.ram.base.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Servicio específico para la entidad TipoEntity
// aquí se pueden definir métodos adicionales específicos para TipoEntity si es necesario



@Service
public class TipoServiceImpl extends BaseServiceImpl<TipoEntity, Long> implements TipoService {

    //Para poder usar métodos específicos del repositorio si es necesario
    @Autowired
    private TipoRepository tipoRepository;

    // Constructor que inyecta el repositorio base
    public TipoServiceImpl(BaseRepository<TipoEntity, Long> baseRepository) {
        super(baseRepository);
    }



}
