package com.ecommerce.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Application configuration class.
 *
 * Configures component scanning, database connection,
 * and JDBC Template.
 */
@Configuration
@ComponentScan(basePackages = "com.ecommerce") // Search The Components
@PropertySource("classpath:application.properties")
public class AppConfig {

    // Database URL
    @Value("${db.url}")
    private String url;

    // Database Uesrname
    @Value("${db.username}")
    private String username;

    // Database password
    @Value("${db.password}")
    private String password;

    // JDBC Driver
    @Value("${db.driver}")
    private String driver;

    /**
     * Creates and returns the application's DataSource.
     *
     * @return configured DataSource
     */
    @Bean
    public DataSource dataSource() {

        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    /**
     * Creates a JdbcTemplate using the configured DataSource.
     *
             * @param dataSource application DataSource
     * @return JdbcTemplate instance
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }
}