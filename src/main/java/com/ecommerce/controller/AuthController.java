package com.ecommerce.controller;

import java.util.Objects;

import com.ecommerce.factory.ServiceFactory;
import com.ecommerce.model.User;
import com.ecommerce.service.AuthService;

public class AuthController {

    private final AuthService authService;

    // Constructor Injection
    public AuthController() {

        this.authService = ServiceFactory.getAuthService();
    }

    // Signup
    public boolean signup(User user) {

        Objects.requireNonNull(user, "User cannot be null");

        return authService.signup(user);
    }

    // Login
    public User login(User user) {

        Objects.requireNonNull(user, "User cannot be null");

        return authService.login(user);
    }

    // Get user by email
    public User getUserByEmail(String email) {

        Objects.requireNonNull(email, "Email cannot be null");

        return authService.getUserByEmail(email);
    }
}