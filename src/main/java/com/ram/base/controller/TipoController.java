package com.ram.base.controller;

import com.ram.base.entity.TipoEntity;
import com.ram.base.service.TipoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tipo")
public class TipoController extends  BaseControllerImpl<TipoEntity, TipoServiceImpl> {


}
