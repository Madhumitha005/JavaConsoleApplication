package com.ecommerce.view;

import java.util.Scanner;
import java.util.Set;

import com.ecommerce.controller.AuthController;
import com.ecommerce.enumtype.Role;
import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.User;
import com.ecommerce.util.InputUtil;
import com.ecommerce.validator.UserValidator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class SignupView {

    // send signup request to controller
    private final AuthController authController;
    private final Scanner scanner;

    // Constructor Injection
    public SignupView(AuthController authController) {

        this.authController = authController;
        this.scanner = InputUtil.getInstance().getScanner();
    }

    private boolean validateField(Validator validator,User user,String fieldName) {

        Set<ConstraintViolation<User>> violations =
                validator.validateProperty(user, fieldName);

        if (violations.isEmpty()) {
            return true;
        }

        for (ConstraintViolation<User> violation : violations) {
             System.out.println(violation.getMessage());
        }
        return false;
    }

    // Signup
    public void show() {

        System.out.println("\n========== SIGNUP ==========");

        // Create user object to store the user details
        User user = new User();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Name
        // Name
        while (true) {

            System.out.print("Enter Name : ");
            user.setName(scanner.nextLine());

            if (validateField(validator, user, "name")) {
                break;
            }
        }
        // Email
        // Email
        while (true) {

            System.out.print("Enter Email : ");
            user.setEmail(scanner.nextLine());

            if (!validateField(validator, user, "email")) {
                continue;
            }

            // Check duplicate email
            if (authController.getUserByEmail(user.getEmail()) != null) {
                System.out.println("Email Already Registered.");
                continue;
            }
            break;
        }

        try {
            // Password
            while (true) {

                System.out.print("Enter Password : ");
                String password = scanner.nextLine();

                try {

                    UserValidator.validatePassword(password);
                    user.setPassword(password);
                    break;

                } catch (ValidationException e) {

                    System.out.println(e.getMessage());
                }
            }

            // Role
            while (true) {

                try {

                    System.out.print("Enter Role (ADMIN/CUSTOMER) : ");

                    String role = scanner.nextLine().trim().toUpperCase();

                    // convert the role from string to enum
                    user.setRole(Role.valueOf(role));

                    break;

                } catch (IllegalArgumentException e) {

                    System.out.println("Invalid Role. Enter ADMIN or CUSTOMER.");
                }
            }

            // Save User and send the signup requesr to contorller
            boolean result = authController.signup(user);

            if (result) {

                System.out.println("Signup Successful.");

            } else {

                System.out.println("Signup Failed.");
            }

        } catch (NullPointerException e) {

            System.out.println("Required Object Is Null.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
}