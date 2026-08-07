/*
 * UserUpdateDto.java
 *
 * Version 1.0
 *
 * August 04, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.user.dto;

import com.ecommerce.common.enums.Role;

// Data transfer object used for updating user details
public class UserUpdateDto {

    // User id
    private Integer id;

    // User name
    private String name;

    // User email address
    private String email;

    // User password
    private String password;

    // User role
    private Role role;

    // Default constructor
    public UserUpdateDto() {
    }

    // Parameterized constructor
    public UserUpdateDto(
            final Integer id,
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

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    @Override
    public String toString() {

        return "UserUpdateDto{"
                + "id="
                + id
                + ", name='"
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