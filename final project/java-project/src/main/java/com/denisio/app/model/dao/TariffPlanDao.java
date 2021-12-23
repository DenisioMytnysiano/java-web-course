package com.denisio.app.model.dao;

import com.denisio.app.model.entity.Service;
import com.denisio.app.model.entity.TariffPlan;
import com.denisio.app.utils.ConnectionPool;
import com.denisio.app.utils.FilterConfiguration;
import com.denisio.app.utils.OrderBy;
import com.denisio.app.utils.SortingDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class TariffPlanDao {

    final static Logger logger = LogManager.getLogger(TariffPlanDao.class);
    public static final String GET_TARIFF_PLAN_BY_ID_SQL = "SELECT s.description as service_description, t.description tariff_description, * FROM TariffPlans t " +
            "JOIN Services s on t.service_id = s.service_id " +
            "WHERE tariff_plan_id = ?";

    public static final String GET_TARIFF_PLANS_SQL = "SELECT * FROM TariffPlans " +
            "JOIN Services on TariffPlans.service_id = Services.service_id " +
            "ORDER BY %s %s";

    public static final String DELETE_TARIFF_PLAN_BY_ID_SQL = "DELETE FROM TariffPlans " +
            "WHERE tariff_plan_id = ?";

    public static final  String ADD_TARIFF_PLAN_SQL = "INSERT INTO TariffPlans (tariff_plan_name, service_id, duration, price, description) " +
            "VALUES (?, ?, ?, ?, ?)";

    public static final String UPDATE_TARIFF_PLAN_SQL = "update TariffPlans set " +
            "tariff_plan_name=?, service_id=?, " +
            "duration=?, price=?, " +
            "description=? " +
            "where tariff_plan_id=?";

    public Optional<TariffPlan> getTariffPlanById(Long id) {
        
        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_TARIFF_PLAN_BY_ID_SQL)
        ) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next())
                    return Optional.empty();

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
                logger.info("DB | TariffPlan with id: {} was found ", id);
                return Optional.of(tariffPlan);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return Optional.empty();
    }

    public List<TariffPlan> getTariffPlans(FilterConfiguration configuration) {

        List<TariffPlan> tariffPlans = new LinkedList<>();
        OrderBy orderBy = configuration.getOrderBy();
        SortingDirection direction = configuration.getSortingDirection();

        try (
                Connection connection = ConnectionPool.getConnection();
                Statement statement = connection.createStatement()
        ) {
            try (ResultSet resultSet = statement.executeQuery(String.format(GET_TARIFF_PLANS_SQL, orderBy.toString().toLowerCase(Locale.ROOT), direction.toString().toLowerCase(Locale.ROOT)))) {
                while (resultSet.next()) {

                    Service service = new Service();
                    service.setServiceName(resultSet.getString("service_name"));
                    service.setDescription(resultSet.getString("description"));
                    service.setId(resultSet.getLong("service_id"));
                    TariffPlan tariffPlan = new TariffPlan();
                    tariffPlan.setId(resultSet.getLong("tariff_plan_id"));
                    tariffPlan.setDescription(resultSet.getString("description"));
                    tariffPlan.setDuration(resultSet.getInt("duration"));
                    tariffPlan.setTariffPlanName(resultSet.getString("tariff_plan_name"));
                    tariffPlan.setPrice(resultSet.getBigDecimal("price"));
                    tariffPlan.setService(service);
                    tariffPlans.add(tariffPlan);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return tariffPlans;
    }


    public void deleteTariffPlanById(Long id) {
        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TARIFF_PLAN_BY_ID_SQL)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info("DB | User was updated: {}", id);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void addTariffPlan(TariffPlan tariffPlan){
        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ADD_TARIFF_PLAN_SQL)
        ) {
            preparedStatement.setString(1, tariffPlan.getTariffPlanName());
            preparedStatement.setLong(2, tariffPlan.getService().getId());
            preparedStatement.setInt(3, tariffPlan.getDuration());
            preparedStatement.setBigDecimal(4, tariffPlan.getPrice());
            preparedStatement.setString(5, tariffPlan.getDescription());
            preparedStatement.executeUpdate();
            logger.info("DB | User was inserted: {}", tariffPlan);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void updateTariffPlan(Long id, TariffPlan tariffPlan){
        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TARIFF_PLAN_SQL)
        ) {
            preparedStatement.setString(1, tariffPlan.getTariffPlanName());
            preparedStatement.setLong(2, tariffPlan.getService().getId());
            preparedStatement.setInt(3, tariffPlan.getDuration());
            preparedStatement.setBigDecimal(4, tariffPlan.getPrice());
            preparedStatement.setString(5, tariffPlan.getDescription());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
            logger.info("DB | User was updated: {}", tariffPlan);
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
