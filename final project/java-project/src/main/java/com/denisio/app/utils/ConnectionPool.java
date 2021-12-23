package com.denisio.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool {

    private static Connection connection;

    public static Connection getConnection() {
        try {
            ResourceBundle config = ResourceBundle.getBundle("config");
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    config.getString("url"),
                    config.getString("user"),
                    config.getString("password")
            );

        } catch (ClassNotFoundException e) {
            System.out.println("DB driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to DB: " + e.getMessage());
        }
        return connection;
    }
}
