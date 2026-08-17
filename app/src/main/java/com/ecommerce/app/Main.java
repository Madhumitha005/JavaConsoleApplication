/*
 * Main.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/**
 * Entry point of the E-Commerce application.
 * This class initializes the Spring Boot application.
 */
@SpringBootApplication(scanBasePackages = "com.ecommerce")
@EnableCaching
public class Main {

    // Starts the Spring Boot application.
    public static void main(final String[] args) {

        SpringApplication.run(Main.class, args);
    }
}