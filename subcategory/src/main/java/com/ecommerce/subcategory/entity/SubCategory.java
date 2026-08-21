/*
 * SubCategory.java
 *
 * Version 1.2
 *
 * July 31, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.subcategory.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.ecommerce.category.entity.Category;
import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// Represents a product subcategory
@Entity
@Table(name = "sub_category")
public class SubCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(
            message = "Invalid subcategory ID.",
            groups = UpdateGroup.class
    )
    @Column(name = "subcategory_id")
    private Integer subCategoryId;

    @NotBlank(
            message = "Subcategory name cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            min = 3,
            max = 50,
            message = "Subcategory name must contain 3 to 50 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z ]*$",
            message = "Subcategory name must contain only letters and spaces.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Column(
            name = "subcategory_name",
            nullable = false,
            unique = true,
            length = 50
    )
    private String subCategoryName;

    // Parent category of this subcategory
    @NotNull(
            message = "Category cannot be null.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private Category category;

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

    // Default constructor required by JPA
    public SubCategory() {
    }

    // Parameterized constructor
    public SubCategory(
            final Integer subCategoryId,
            final String subCategoryName,
            final Category category,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {

        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(
            final Integer subCategoryId) {

        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(
            final String subCategoryName) {

        this.subCategoryName = subCategoryName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(
            final Category category) {

        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            final LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            final LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {

        return "SubCategory{"
                + "subCategoryId="
                + subCategoryId
                + ", subCategoryName='"
                + subCategoryName
                + '\''
                + ", categoryId="
                + (category != null
                ? category.getCategoryId()
                : null)
                + '}';
    }
}
