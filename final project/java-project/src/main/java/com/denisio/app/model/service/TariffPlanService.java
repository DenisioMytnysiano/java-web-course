package com.denisio.app.model.service;

import com.denisio.app.model.dao.ServiceDao;
import com.denisio.app.model.dao.TariffPlanDao;
import com.denisio.app.model.entity.Service;
import com.denisio.app.model.entity.TariffPlan;
import com.denisio.app.utils.FilterConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class TariffPlanService {

    final private TariffPlanDao tariffPlanDao = new TariffPlanDao();
    final private ServiceDao serviceDao = new ServiceDao();
    final static Logger logger = LogManager.getLogger(TariffPlanService.class);

    public List<TariffPlan> getTariffPlans(FilterConfiguration configuration) {
        return tariffPlanDao.getTariffPlans(configuration);
    }

    public Optional<TariffPlan> getTariffPlanById(Long id) {
        return tariffPlanDao.getTariffPlanById(id);
    }

    public void deleteTariffPlanById(Long id){
        tariffPlanDao.deleteTariffPlanById(id);
    }

    public void addTariffPlan(TariffPlan tariffPlan){
        Optional<Service> service = serviceDao.getServiceByName(tariffPlan.getService().getServiceName());
        if (service.isPresent()){
            tariffPlan.setService(service.get());
            tariffPlanDao.addTariffPlan(tariffPlan);
        }
        else
            logger.info("Failed to add tariff plan, service with name {} not found", tariffPlan.getService().getServiceName());
    }

    public void updateTariffPlan(Long id, TariffPlan tariffPlan){
        Optional<Service> service = serviceDao.getServiceByName(tariffPlan.getService().getServiceName());
        if (service.isPresent()){
            tariffPlan.setService(service.get());
            tariffPlanDao.updateTariffPlan(id, tariffPlan);
        }
        else
            logger.info("Failed to update tariff plan, service with name {} not found", tariffPlan.getService().getServiceName());
    }
}
