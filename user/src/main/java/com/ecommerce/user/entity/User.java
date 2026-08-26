/*
 * User.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ecommerce.common.enums.Role;
import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// Represents a user
@Entity
@Table(name = "\"user\"")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    // User id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(
            message = "Invalid user ID.",
            groups = UpdateGroup.class
    )
    private Integer id;

    // User name
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
            message = "Name must contain 2 to 30 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z ]*$",
            message = "Name must contain only letters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Column(
            nullable = false,
            length = 30
    )
    private String name;

    // User email address
    @NotBlank(
            message = "Email is required.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Email(
            message = "Enter a valid email address.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            max = 50,
            message = "Email cannot exceed 50 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Column(
            nullable = false,
            unique = true,
            length = 50
    )
    private String email;

    // User password
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
            message = "Password must contain 6 to 15 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^(?=.*[a-z])"
                    + "(?=.*[A-Z])"
                    + "(?=.*\\d)"
                    + "(?=.*[@#$%^&+=!]).{6,15}$",
            message = "Password must contain uppercase, "
                    + "lowercase, number and special character.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Column(
            nullable = false,
            length = 255
    )
    private String password;

    // User role
    @NotNull(
            message = "Role is required.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Convert(converter = RoleConverter.class)
    @Column(nullable = false)
    private Role role;

    // Account creation time
    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    // Last modification time
    @UpdateTimestamp
    @Column(
            name = "updated_at"
    )
    private LocalDateTime updatedAt;

    // Last login time
    @Column(
            name = "last_login_at"
    )
    private LocalDateTime lastLoginAt;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(
            final Integer id,
            final String name,
            final String email,
            final String password,
            final Role role,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime lastLoginAt) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLoginAt = lastLoginAt;
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(final LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    @Override
    public String toString() {

        return "User{"
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
                + ", lastLoginAt="
                + lastLoginAt
                + '}';
    }

    /*
     * Converts Role enum values to database integer values
     * and database integer values back to Role enum values.
     */
    @Converter
    public static class RoleConverter
            implements AttributeConverter<Role, Integer> {

        @Override
        public Integer convertToDatabaseColumn(final Role role) {

            if (role == null) {
                return null;
            }

            return role.getId();
        }

        @Override
        public Role convertToEntityAttribute(final Integer id) {

            if (id == null) {
                return null;
            }

            return Role.fromId(id);
        }
    }
}