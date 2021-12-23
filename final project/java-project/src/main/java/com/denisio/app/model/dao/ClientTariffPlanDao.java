package com.denisio.app.model.dao;

import com.denisio.app.model.entity.ClientTariffPlan;
import com.denisio.app.model.entity.Service;
import com.denisio.app.model.entity.TariffPlan;
import com.denisio.app.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ClientTariffPlanDao {

    public static final String GET_TARIFF_PLANS_BY_EMAIL_SQL = "SELECT S.description as service_description, TP.description tariff_description, * FROM ClientTariffPlans " +
            "JOIN Clients C on ClientTariffPlans.client_id = C.client_id " +
            "JOIN TariffPlans TP on TP.tariff_plan_id = ClientTariffPlans.tariff_plan_id " +
            "JOIN Services S on S.service_id = TP.service_id " +
            "WHERE email = ? AND end_date > CURRENT_TIMESTAMP";
    public static final String ADD_TARIFF_PLAN_SQL = "INSERT INTO ClientTariffPlans (client_id, tariff_plan_id, start_date, end_date)" +
            "VALUES (?, ?, ?, ?)";
    final static Logger logger = LogManager.getLogger(ClientTariffPlanDao.class);
    final static ClientDao clientDao = new ClientDao();

    public List<ClientTariffPlan> getClientTariffPlansByEmail(String email) {
        List<ClientTariffPlan> clientTariffPlans = new LinkedList<>();

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_TARIFF_PLANS_BY_EMAIL_SQL)
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Service service = new Service();
                    service.setServiceName(resultSet.getString("service_name"));
                    service.setDescription(resultSet.getString("service_description"));
                    service.setId(resultSet.getLong("service_id"));

                    TariffPlan tariffPlan = new TariffPlan();
                    tariffPlan.setId(resultSet.getLong("tariff_plan_id"));
                    tariffPlan.setDescription(resultSet.getString("tariff_description"));
                    tariffPlan.setDuration(resultSet.getInt("duration"));
                    tariffPlan.setTariffPlanName(resultSet.getString("tariff_plan_name"));
                    tariffPlan.setPrice(resultSet.getBigDecimal("price"));
                    tariffPlan.setService(service);

                    ClientTariffPlan clientTariffPlan = new ClientTariffPlan();
                    clientTariffPlan.setClient(clientDao.getClientByEmail(email).get());
                    clientTariffPlan.setTariffPlan(tariffPlan);
                    clientTariffPlan.setId(resultSet.getLong("client_tariff_plan_id"));
                    clientTariffPlan.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());
                    clientTariffPlan.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());
                    clientTariffPlans.add(clientTariffPlan);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.info("DB | Extracted {} ClientTariffPlans for user with email {}.", clientTariffPlans.size(), email);
        return clientTariffPlans;
    }

    public void addClientTariffPlan(ClientTariffPlan clientTariffPlan) {
        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ADD_TARIFF_PLAN_SQL)
        ) {
            preparedStatement.setLong(1, clientTariffPlan.getClient().getId());
            preparedStatement.setLong(2, clientTariffPlan.getTariffPlan().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(clientTariffPlan.getStartDate()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(clientTariffPlan.getEndDate()));
            preparedStatement.executeUpdate();
            logger.info("DB | ClientTariffPlan was added: {}", clientTariffPlan);
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
