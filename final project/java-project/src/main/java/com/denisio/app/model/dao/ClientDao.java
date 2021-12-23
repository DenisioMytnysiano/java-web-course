package com.denisio.app.model.dao;

import com.denisio.app.model.entity.Client;
import com.denisio.app.model.entity.UserRole;
import com.denisio.app.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class ClientDao {

    public static final String ADD_CLIENT_SQL = "INSERT INTO Clients (client_name, surname, password, email, phone_number, account, blocked, client_role) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_CLIENT_SQL = "update clients set " +
            "client_name=?, surname=?, " +
            "password=?, " +
            "blocked=?, phone_number=?, " +
            "email=?, account=?, client_role=?" +
            "where client_id=?";

    public static final String GET_CLIENT_BY_EMAIL_SQL = "SELECT client_id, client_name, surname, password, email, phone_number, account, blocked, client_role " +
            "from clients where email=?";

    public static final String GET_CLIENT_BY_EMAIL_AND_PASSWORD_SQL = "SELECT client_id, client_name, surname, password, email, phone_number, account, blocked, client_role FROM Clients WHERE email=? and password=?";
    final static Logger logger = LogManager.getLogger(ClientDao.class);

    public void addClient(Client client) {

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ADD_CLIENT_SQL)
        ) {
            preparedStatement.setString(1, client.getClientName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setString(3, client.getPassword());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getPhoneNumber());
            preparedStatement.setBigDecimal(6, client.getAccount());
            preparedStatement.setBoolean(7, client.isBlocked());
            preparedStatement.setString(8, client.getRole().toString());

            preparedStatement.executeUpdate();
            logger.info("DB | User was inserted: {}", client);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void updateClient(Client client) {

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_SQL)
        ) {
            preparedStatement.setString(1, client.getClientName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setString(3, client.getPassword());
            preparedStatement.setBoolean(4, client.isBlocked());
            preparedStatement.setString(5, client.getPhoneNumber());
            preparedStatement.setString(6, client.getEmail());
            preparedStatement.setBigDecimal(7, client.getAccount());
            preparedStatement.setString(8, client.getRole().toString());
            preparedStatement.setLong(9, client.getId());
            preparedStatement.executeUpdate();
            logger.info("DB | User was updated: {}", client);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public Optional<Client> getClientByEmail(String email) {

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENT_BY_EMAIL_SQL)
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next())
                    return Optional.empty();
                Client user = new Client();
                user.setId(resultSet.getLong("client_id"));
                user.setClientName(resultSet.getString("client_name"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setSurname(resultSet.getString("surname"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(UserRole.valueOf(resultSet.getString("client_role")));
                user.setBlocked(resultSet.getBoolean("blocked"));
                user.setEmail(resultSet.getString("email"));
                user.setAccount(resultSet.getBigDecimal("account"));
                logger.info("DB | User with email: {} was found ", email);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return Optional.empty();
    }

    public Optional<Client> getUserByEmailAndPassword(String email, String password) {
        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENT_BY_EMAIL_AND_PASSWORD_SQL)
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next())
                    return Optional.empty();
                Client user = new Client();
                user.setId(resultSet.getLong("client_id"));
                user.setClientName(resultSet.getString("client_name"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setSurname(resultSet.getString("surname"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(UserRole.valueOf(resultSet.getString("client_role")));
                user.setBlocked(resultSet.getBoolean("blocked"));
                user.setEmail(resultSet.getString("email"));
                user.setAccount(resultSet.getBigDecimal("account"));
                logger.info("DB | User with email " + email + " was found | " + user);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return Optional.empty();
    }
}
