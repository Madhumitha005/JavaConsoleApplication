package com.ecommerce.view;

import java.util.Scanner;

import com.ecommerce.controller.AuthController;
import com.ecommerce.enumtype.Role;
import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.User;
import com.ecommerce.util.InputUtil;
import com.ecommerce.validator.UserValidator;
import com.ecommerce.view.admin.AdminDashboardView;
import com.ecommerce.view.customer.CustomerDashboardView;

public class LoginView {

    private final AuthController authController;
    private final Scanner scanner;
    private final AdminDashboardView adminDashboardView;
    private final CustomerDashboardView customerDashboardView;

    // Required object inject through Constructor Injection
    public LoginView(AuthController authController,
                     AdminDashboardView adminDashboardView,
                     CustomerDashboardView customerDashboardView) {

        this.authController = authController;
        this.adminDashboardView = adminDashboardView;
        this.customerDashboardView = customerDashboardView;
        this.scanner = InputUtil.getInstance().getScanner();
    }

    // Login
    public void show() {

        System.out.println("\n========== LOGIN ==========");

        User user = new User();

        try {

            // Email
            while (true) {

                System.out.print("Enter Email : ");
                user.setEmail(scanner.nextLine());

                try {

                    UserValidator.validateEmail(user.getEmail());
                    break;

                } catch (ValidationException e) {

                    System.out.println(e.getMessage());
                }
            }

            // Password
            while (true) {

                System.out.print("Enter Password : ");
                user.setPassword(scanner.nextLine());

                try {

                    UserValidator.validatePassword(user.getPassword());
                    break;

                } catch (ValidationException e) {

                    System.out.println(e.getMessage());
                }
            }

            // send loginrequest to controller
            User loggedInUser = authController.login(user);

            // Data available or not in db
            if (loggedInUser == null) {

                System.out.println("Invalid Email Or Password.");
                return;
            }

            System.out.println("\nLogin Successful.");
            System.out.println("Welcome : " + loggedInUser.getName());
            System.out.println("Role    : " + loggedInUser.getRole());

            // Role check
            Role role = loggedInUser.getRole();

            switch (role) {

                case ADMIN -> {

                    System.out.println("\nAdmin Dashboard...");
                    adminDashboardView.show();
                }

                case CUSTOMER -> {

                    System.out.println("\nCustomer Dashboard...");
                    customerDashboardView.show(loggedInUser);
                }

                default ->

                    System.out.println("Invalid Role.");
            }

        } catch (NullPointerException e) {

            System.out.println("Required Object Is Null");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
}