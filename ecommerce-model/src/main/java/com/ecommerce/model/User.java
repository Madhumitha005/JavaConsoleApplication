/*
 * User.java
 *
 * Version 1.3
 *
 * July 25, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.model;

import com.ecommerce.common.enums.Role;
import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class User {

    // User Id
    @Positive(
            message = "Invalid User ID.",
            groups = UpdateGroup.class
    )
    private int id;

    // Name
    @NotBlank(
            message = "Name cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            min = 2,
            max = 30,
            message =
                    "Name must be between "
                            + "2 and 30 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z ]*$",
            message =
                    "Name must contain only "
                            + "letters and spaces.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String name;

    // Email
    @NotBlank(
            message = "Email is required.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            max = 50,
            message =
                    "Email cannot exceed "
                            + "50 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Email(
            message = "Enter a valid email.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String email;

    // Password
    @NotBlank(
            message = "Password cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            min = 6,
            max = 15,
            message =
                    "Password must be between "
                            + "6 and 15 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*\\d)" + "(?=.*[@#$%^&+=!])" + ".{6,15}$",
            message =
                    "Password must contain uppercase, "
                            + "lowercase, number and "
                            + "special character.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String password;

    // Role
    @NotNull(
            message = "Role is required.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private Role role;

    // Default Constructor
    public User() {
    }

    // Parametarized Constructor
    public User(
            final int id,
            final String name,
            final String email,
            final String password,
            final Role role) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Get User Id
    public int getId() {

        return id;
    }

    //Set User Id
    public void setId(final int id) {

        this.id = id;
    }

    // Get Name
    public String getName() {

        return name;
    }

    // Set Name
    public void setName(final String name) {

        this.name = name;
    }

    // Get Email
    public String getEmail() {

        return email;
    }

    // Set Email
    public void setEmail(final String email) {

        this.email = email;
    }

    // GEt Password
    public String getPassword() {

        return password;
    }

    // Set Password
    public void setPassword(final String password) {

        this.password = password;
    }

    // Get Role
    public Role getRole() {

        return role;
    }

    // Set Role
    public void setRole(final Role role) {

        this.role = role;
    }

    @Override
    public String toString() {

        return "User{"
                + "id="
                + id
                + ", name='"
                + name
                + ", email='"
                + email
                + ", role="
                + role
                + '}';
    }
}
