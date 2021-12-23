package com.denisio.app.model.dao;

import com.denisio.app.model.entity.Service;
import com.denisio.app.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ServiceDao {

    public static final String GET_SERVICE_BY_NAME_SQL = "SELECT service_id, service_name, description " +
            "from services where service_name=?";
    final static Logger logger = LogManager.getLogger(ClientDao.class);

    public Optional<Service> getServiceByName(String name) {

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_SERVICE_BY_NAME_SQL)
        ) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    logger.info("Service by name {} not found in the database", name);
                    return Optional.empty();
                }
                Service service = new Service();
                service.setId(resultSet.getLong("service_id"));
                service.setServiceName(resultSet.getString("service_name"));
                service.setDescription(resultSet.getString("description"));
                logger.info("Service by name {} found in the database", name);
                return Optional.of(service);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return Optional.empty();
    }
}
