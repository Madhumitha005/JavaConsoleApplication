package com.ecommerce.validator;

import com.ecommerce.enumtype.Role;
import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.User;

public final class UserValidator {

    private UserValidator() {
    }

    // Validate Complete User
    public static void validate(User user) {

        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }

        validateName(user.getName());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        validateRole(user.getRole());
    }

    // Name Validation
    public static void validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty.");
        }

        if (!name.matches("[A-Za-z ]{1,30}")) {
            throw new ValidationException("Invalid Name.");
        }
    }

    // Email Validation
    public static void validateEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty.");
        }

        String pattern ="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!email.matches(pattern)) {
            throw new ValidationException("Invalid Email.");
        }
    }

    // Password Validation
    public static void validatePassword(String password) {

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password cannot be empty.");
        }

        String pattern ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$";

        if (!password.matches(pattern)) {
            throw new ValidationException(
                    "Password must contain uppercase, lowercase, number, special character and minimum 6 characters."
            );
        }
    }

    // Role Validation
    public static void validateRole(Role role) {

        if (role == null) {
            throw new ValidationException("Role must be selected.");
        }
    }
}