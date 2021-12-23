package com.denisio.app.model.service;

import com.denisio.app.model.dao.ClientDao;
import com.denisio.app.model.entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;

public class ClientService {

    final static Logger logger = LogManager.getLogger(ClientService.class);
    final static ClientDao clientDao = new ClientDao();

    public void registerClient(Client client) {
        if (client != null){
            clientDao.addClient(client);
            logger.info("User {} {} was registered", client.getClientName(), client.getSurname());
        }
    }

    public void unblockClient(String email) {
        if (email != null) {
            Optional<Client> optionalUser = clientDao.getClientByEmail(email);
            if (optionalUser.isPresent()) {
                Client client = optionalUser.get();
                client.setBlocked(false);
                clientDao.updateClient(client);
                logger.info("User with email: {} was unblocked", email);
            } else
                logger.info("User with email: {} wasn't found", email);
        }
    }

    public void blockClient(String email) {
        if (email != null) {
            Optional<Client> optionalUser = clientDao.getClientByEmail(email);
            if (optionalUser.isPresent()) {
                Client client = optionalUser.get();
                client.setBlocked(true);
                clientDao.updateClient(client);
                logger.info("User with email: {} was blocked", email);
            } else
                logger.info("User with email: {} wasn't found", email);
        }
    }

    public Optional<Client> getClient(String email, String password){
        return clientDao.getUserByEmailAndPassword(email, password);
    }

    public void replenishAccount(Client client, BigDecimal amount){
        client.setAccount(client.getAccount().add(amount));
        clientDao.updateClient(client);
        logger.info("Account for client {} {} replenished on {}", client.getClientName(), client.getSurname(), amount);
    }
}
