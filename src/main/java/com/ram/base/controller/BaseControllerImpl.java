package com.ram.base.controller;

import com.ram.base.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class BaseControllerImpl<E, S extends BaseServiceImpl <E, Long> implements BaseController<E, Long> {

@Autowired
protected S service;

}
