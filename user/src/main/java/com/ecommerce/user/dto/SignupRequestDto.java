/*
 * SignupRequestDto.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.dto;

import com.ecommerce.common.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Data transfer object used for user registration requests
public class SignupRequestDto {

    // User name
    @NotBlank(
            message = "Name is required."
    )
    @Size(
            min = 2,
            max = 30,
            message = "Name must contain 2 to 30 characters."
    )
    private String name;

    // User email
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
            message = "Password must contain "
                    + "6 to 15 characters."
    )
    private String password;

    // User role
    @NotNull(
            message = "Role is required."
    )
    private Role role;

    // Default constructor
    public SignupRequestDto() {
    }

    // Parameterized constructor
    public SignupRequestDto(
            final String name,
            final String email,
            final String password,
            final Role role) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Returns user name
    public String getName() {

        return name;
    }

    // Sets user name
    public void setName(final String name) {

        this.name = name;
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

    // Returns user role
    public Role getRole() {

        return role;
    }

    // Sets user role
    public void setRole(final Role role) {

        this.role = role;
    }

    @Override
    public String toString() {

        return "SignupRequestDto{"
                + "name='"
                + name
                + '\''
                + ", email='"
                + email
                + '\''
                + ", role="
                + role
                + '}';
    }
}