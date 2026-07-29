package com.ecommerce.view;

import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.common.enums.Role;
import com.ecommerce.common.util.InputUtil;
import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.controller.AuthController;
import com.ecommerce.model.User;
import com.ecommerce.view.admin.AdminDashboardView;
import com.ecommerce.view.customer.CustomerDashboardView;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class LoginView {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginView.class);

    private final AuthController authController;
    private final AdminDashboardView adminDashboardView;
    private final CustomerDashboardView customerDashboardView;
    private final Scanner scanner;

    public LoginView(
            final AuthController authController,
            final AdminDashboardView adminDashboardView,
            final CustomerDashboardView customerDashboardView) {

        this.authController = Objects.requireNonNull(authController, "AuthController cannot be null");
        this.adminDashboardView = Objects.requireNonNull(adminDashboardView, "AdminDashboardView cannot be null");
        this.customerDashboardView = Objects.requireNonNull(customerDashboardView, "CustomerDashboardView cannot be null");
        this.scanner = InputUtil.getInstance().getScanner();
    }

    private boolean validateField(
            final Validator validator,
            final User user,
            final String fieldName) {

        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, fieldName, CreateGroup.class);

        if (violations.isEmpty()) {

            return true;
        }

        for (final ConstraintViolation<User> violation : violations) {

            System.out.println(violation.getMessage());
        }

        return false;
    }

    public void show() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        try {

            LOGGER.info("Login Page Opened");

            System.out.println("\n========== LOGIN ==========");

            User user = new User();

            while (true) {

                System.out.print("Enter Email : ");

                user.setEmail(scanner.nextLine().trim());

                if (validateField(validator, user, "email")) {

                    break;
                }
            }

            while (true) {

                System.out.print("Enter Password : ");

                user.setPassword(scanner.nextLine());

                if (validateField(validator, user, "password")) {

                    break;
                }
            }

            User loggedInUser = authController.login(user);

            if (loggedInUser == null) {

                LOGGER.warn("Invalid Login : {}", user.getEmail());

                System.out.println("Invalid Email Or Password.");

                return;
            }

            LOGGER.info("Login Success : {}", loggedInUser.getEmail());

            System.out.println("\nLogin Successful");
            System.out.println("Welcome : " + loggedInUser.getName());

            Role role = loggedInUser.getRole();

            switch (role) {

                case ADMIN -> adminDashboardView.show();

                case CUSTOMER -> customerDashboardView.show(loggedInUser);

                default -> System.out.println("Invalid Role");
            }

        } catch (final Exception exception) {

            LOGGER.error("Login Error", exception);

            System.out.println("Error : " + exception.getMessage());

        } finally {

            factory.close();
        }
    }
}
