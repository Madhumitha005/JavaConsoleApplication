package com.ecommerce.controller;

import java.util.Objects;

import org.springframework.stereotype.Controller;

import com.ecommerce.model.User;
import com.ecommerce.service.AuthService;

@Controller
public class AuthController {

    private final AuthService authService;

    // Constructor Injection
    public AuthController(final AuthService authService) {

        this.authService = Objects.requireNonNull(authService, "AuthService cannot be null");
    }

    // Signup
    public boolean signup(final User user) {

        Objects.requireNonNull(user, "User cannot be null");

        return authService.signup(user);
    }

    // Login
    public User login(final User user) {

        Objects.requireNonNull(user, "User cannot be null");

        return authService.login(user);
    }

    // Get User By Email
    public User getUserByEmail(final String email) {

        Objects.requireNonNull(email, "Email cannot be null");

        return authService.getUserByEmail(email);
    }
}