package com.denisio.app.model.service;

import com.denisio.app.model.dao.ClientDao;
import com.denisio.app.model.dao.ClientTariffPlanDao;
import com.denisio.app.model.dao.TariffPlanDao;
import com.denisio.app.model.entity.Client;
import com.denisio.app.model.entity.ClientTariffPlan;
import com.denisio.app.model.entity.TariffPlan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ClientTariffPlanService {

    final private ClientTariffPlanDao clientTariffPlanDao = new ClientTariffPlanDao();
    final private TariffPlanDao tariffPlanDao = new TariffPlanDao();
    final private ClientService clientService = new ClientService();
    final private ClientDao clientDao = new ClientDao();
    final static Logger logger = LogManager.getLogger(ClientService.class);

    public List<ClientTariffPlan> getTariffPlansByUser(Client client) {
        return clientTariffPlanDao.getClientTariffPlansByEmail(client.getEmail());
    }

    public void buyTariffPlan(Client client, Long tariffPlanId) {
        Optional<TariffPlan> tariff = tariffPlanDao.getTariffPlanById(tariffPlanId);
        if (tariff.isPresent()) {
            if (client.getAccount().compareTo(tariff.get().getPrice()) < 0) {
                clientService.blockClient(client.getEmail());
                logger.info("Client {} {} was blocked, not enough money to buy tariff plan", client.getClientName(), client.getSurname());
            } else {
                ClientTariffPlan clientTariffPlan = new ClientTariffPlan();
                LocalDateTime purchaseDate = LocalDateTime.now();
                clientTariffPlan.setClient(client);
                clientTariffPlan.setStartDate(purchaseDate);
                clientTariffPlan.setEndDate(purchaseDate.plusDays(tariff.get().getDuration()));
                clientTariffPlan.setTariffPlan(tariff.get());
                clientTariffPlanDao.addClientTariffPlan(clientTariffPlan);
                client.setAccount(client.getAccount().subtract(tariff.get().getPrice()));
                clientDao.updateClient(client);
                logger.info("Tariff plan for client {} {} bought successfully", client.getClientName(), client.getSurname());
            }
        }
    }
}
