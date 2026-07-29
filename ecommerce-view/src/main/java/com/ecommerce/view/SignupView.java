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

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class SignupView {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupView.class);

    private final AuthController authController;
    private final Scanner scanner;

    public SignupView(final AuthController authController) {

        this.authController = Objects.requireNonNull(authController, "AuthController cannot be null.");
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

        LOGGER.info("Signup Page Opened.");

        System.out.println("\n========== SIGNUP ==========");

        User user = new User();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        try {

            while (true) {

                System.out.print("Enter Name : ");

                user.setName(scanner.nextLine().trim());

                if (validateField(validator, user, "name")) {

                    break;
                }
            }

            while (true) {

                System.out.print("Enter Email : ");

                user.setEmail(scanner.nextLine().trim());

                if (!validateField(validator, user, "email")) {

                    continue;
                }

                if (authController.getUserByEmail(user.getEmail()) != null) {

                    System.out.println("Email Already Registered.");

                    continue;
                }

                break;
            }

            while (true) {

                System.out.print("Enter Password : ");

                user.setPassword(scanner.nextLine());

                if (validateField(validator, user, "password")) {

                    break;
                }
            }

            while (true) {

                try {

                    System.out.print("Enter Role " + "(ADMIN/CUSTOMER) : ");

                    String roleInput = scanner.nextLine().trim().toUpperCase();

                    user.setRole(Role.valueOf(roleInput));

                    break;

                } catch (final IllegalArgumentException exception) {

                    System.out.println("Invalid Role. " + "Enter ADMIN " + "or CUSTOMER.");
                }
            }

            boolean result = authController.signup(user);

            if (result) {

                LOGGER.info("Signup Successful : {}", user.getEmail());

                System.out.println("Signup Successful.");

            } else {

                LOGGER.warn("Signup Failed : {}", user.getEmail());

                System.out.println("Signup Failed.");
            }

        } catch (final Exception exception) {

            LOGGER.error("Signup Error.", exception);

            System.out.println("Error : " + exception.getMessage());

        } finally {

            factory.close();
        }
    }
}
