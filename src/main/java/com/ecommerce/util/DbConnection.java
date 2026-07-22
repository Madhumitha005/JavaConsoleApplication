package com.ecommerce.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DbConnection {

    private static Connection connection;

    private DbConnection() {
    }

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                Properties properties = new Properties();
                InputStream input = DbConnection.class
                                .getClassLoader()
                                .getResourceAsStream(
                                        "application.properties");

                if (input == null) {

                    throw new RuntimeException("application.properties not found");
                }
                properties.load(input);
                Class.forName(properties.getProperty("db.driver"));
                connection = DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password")
                );

                LoggerUtil.getInstance()
                        .info("Database Connected Successfully");
            }

        } catch (IOException |SQLException |ClassNotFoundException e) {

            LoggerUtil.getInstance()
                    .error(
                       "Database Connection Error : "
                       + e.getMessage()
                    );
            throw new RuntimeException("Unable to connect database",e);
        }
        return connection;
    }
}