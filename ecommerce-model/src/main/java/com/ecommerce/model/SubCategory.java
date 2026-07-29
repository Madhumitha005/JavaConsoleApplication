/*
 * SubCategory.java
 *
 * Version 1.0
 *
 * July 25, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.model;

import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class SubCategory {

    // Subcategroy ID
    @Positive(
            message = "Invalid Sub Category ID.",
            groups = UpdateGroup.class
    )
    private int subCategoryId;

    // Sub Catgeory Name
    @NotBlank(
            message = "Sub Category Name cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            min = 2,
            max = 50,
            message =
                    "Sub Category Name must be between "
                            + "2 and 50 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z ]*$",
            message =
                    "Sub Category Name must contain "
                            + "only letters and spaces.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String subCategoryName;

    //Category
    @NotNull(
            message = "Category cannot be null.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Valid
    private Category category;

    // Default Constructor
    public SubCategory() {
    }

    // Parametarized Constructor
    public SubCategory(
            final int subCategoryId,
            final String subCategoryName,
            final Category category) {

        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.category = category;
    }

    // Get sub category ID
    public int getSubCategoryId() {

        return subCategoryId;
    }

    // Set sub category Id
    public void setSubCategoryId(final int subCategoryId) {

        this.subCategoryId = subCategoryId;
    }

    // Get Sub Category name
    public String getSubCategoryName() {

        return subCategoryName;
    }

    // Set Sub Category name
    public void setSubCategoryName(final String subCategoryName) {

        this.subCategoryName = subCategoryName;
    }

    // Get Category
    public Category getCategory() {

        return category;
    }

    // Set Category
    public void setCategory(final Category category) {

        this.category = category;
    }

    @Override
    public String toString() {

        return "SubCategory{"
                + "subCategoryId="
                + subCategoryId
                + ", subCategoryName='"
                + subCategoryName
                + ", category="
                + category
                + '}';
    }
}
