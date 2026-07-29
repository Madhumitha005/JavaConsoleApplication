package com.ecommerce.common.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbConnection {

    private static DataSource dataSource;

    @Autowired
    public DbConnection(DataSource dataSource) {

        DbConnection.dataSource = dataSource;
    }

    public static Connection getConnection()
            throws SQLException {

        return dataSource.getConnection();
    }
}