/*
 * @(#)Main.java
 *
 * Version 1.0
 *
 * July 20, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ecommerce.common.util.InputUtil;
import com.ecommerce.config.AppConfig;
import com.ecommerce.view.LoginView;
import com.ecommerce.view.SignupView;


/**
 * Entry point of the E-Commerce System.
 *
 * This class initializes the Spring IoC container,
 * loads the application configuration,
 * and displays the main menu.
 */

public class Main {

    public static void main(String[] args) {

        /* Create Spring IoC Container
           Also Load App Config
         */
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        AppConfig.class
                );

        // Singleton Scanner
        Scanner scanner = InputUtil.getInstance().getScanner();

        // Get Spring Managed Views
        SignupView signupView = context.getBean(SignupView.class);
        LoginView loginView = context.getBean(LoginView.class);

        // Main Menu
        while (true) {

            System.out.println("\n========== E-COMMERCE SYSTEM ==========");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> signupView.show();
                    case 2 -> loginView.show();
                    case 3 -> {
                        System.out.println("Thank You. Visit Again.");

                        scanner.close();
                        context.close();

                        return;
                    }

                    default ->
                            System.out.println("Invalid Choice.");
                }

            } catch (NumberFormatException exception) {

                System.out.println("Please Enter a Valid Number.");
            }
        }
    }
}