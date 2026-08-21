/*
 * Category.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.entity;

import java.io.Serializable;

import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// Represents a category in the e-commerce
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    // Category id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(
            message = "Invalid category ID.",
            groups = UpdateGroup.class
    )
    @Column(name = "category_id")
    private Integer categoryId;

    // Category name
    @NotBlank(
            message = "Category name cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            min = 3,
            max = 50,
            message = "Category name must contain "
                    + "3 to 50 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z ]*$",
            message = "Category name must contain "
                    + "only letters and spaces.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Column(
            name = "category_name",
            nullable = false,
            unique = true,
            length = 50
    )
    private String categoryName;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    // Default constructor
    public Category() {
    }

    // Parameterized constructor
    public Category(
            final Integer categoryId,
            final String categoryName,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Returns the category id
    public Integer getCategoryId() {

        return categoryId;
    }

    // Sets the category id
    public void setCategoryId(final Integer categoryId) {

        this.categoryId = categoryId;
    }

    // Returns the category name
    public String getCategoryName() {

        return categoryName;
    }

    // Sets the category name
    public void setCategoryName(final String categoryName) {

        this.categoryName = categoryName;
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

        return "Category{"
                + "categoryId="
                + categoryId
                + ", categoryName='"
                + categoryName
                + '\''
                + '}';
    }
}