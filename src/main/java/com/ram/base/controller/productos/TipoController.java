package com.ram.base.controller.productos;

import com.ram.base.controller.generic.BaseControllerImpl;
import com.ram.base.entity.productos.TipoEntity;
import com.ram.base.service.productos.TipoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tipo")
public class TipoController extends BaseControllerImpl<TipoEntity, TipoServiceImpl> {


}
