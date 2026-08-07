/*
 * LoginRequestDto.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Data transfer object used for user login requests
public class LoginRequestDto {

    // User email address
    @NotBlank(
            message = "Email is required."
    )
    @Email(
            message = "Invalid email address."
    )
    private String email;

    // User password
    @NotBlank(
            message = "Password is required."
    )
    @Size(
            min = 6,
            max = 15,
            message = "Password must contain 6 to 15 characters."
    )
    private String password;

    // Default constructor
    public LoginRequestDto() {
    }

    // Parameterized constructor
    public LoginRequestDto(
            final String email,
            final String password) {

        this.email = email;
        this.password = password;
    }

    // Returns user email
    public String getEmail() {

        return email;
    }

    // Sets user email
    public void setEmail(final String email) {

        this.email = email;
    }

    // Returns user password
    public String getPassword() {

        return password;
    }

    // Sets user password
    public void setPassword(final String password) {

        this.password = password;
    }

    @Override
    public String toString() {

        return "LoginRequestDto{"
                + "email='"
                + email
                + '\''
                + '}';
    }
}