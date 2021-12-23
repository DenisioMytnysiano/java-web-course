package com.denisio.app.model.service;

import com.denisio.app.model.dao.ServiceDao;
import com.denisio.app.model.entity.Service;

import java.util.Optional;

public class ServiceService {

    private final ServiceDao serviceDao = new ServiceDao();

    public Optional<Service> getServiceByName(String name){
        return serviceDao.getServiceByName(name);
    }
}
