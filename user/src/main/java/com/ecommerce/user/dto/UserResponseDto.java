/*
 * UserResponseDto.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.dto;

import java.time.LocalDateTime;

import com.ecommerce.common.enums.Role;

// Data transfer object used for sending user information
public class UserResponseDto {

    // User id
    private Integer id;

    // User name
    private String name;

    // User email address
    private String email;

    // User role
    private Role role;

    // Record creation time
    private LocalDateTime createdAt;

    // Record update time
    private LocalDateTime updatedAt;

    // Default constructor
    public UserResponseDto() {
    }

    // Parameterized constructor
    public UserResponseDto(
            final Integer id,
            final String name,
            final String email,
            final Role role,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Role getRole() {

        return role;
    }

    public void setRole(final Role role) {

        this.role = role;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {

        return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {

        return "UserResponseDto{"
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
                + ", createdAt="
                + createdAt
                + ", updatedAt="
                + updatedAt
                + '}';
    }
}