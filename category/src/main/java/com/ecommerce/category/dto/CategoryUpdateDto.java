/*
 * CategoryUpdateDto.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object used for
 * category update requests.
 */
public class CategoryUpdateDto {

    // Category id
    @NotNull(
            message = "Category ID is required."
    )
    private Integer categoryId;

    // Category name
    @NotBlank(
            message = "Category name is required."
    )
    @Size(
            min = 3,
            max = 50,
            message = "Category name must contain "
                    + "3 to 50 characters."
    )
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z ]*$",
            message = "Category name must contain "
                    + "only letters and spaces."
    )
    private String categoryName;

    // Default constructor
    public CategoryUpdateDto() {
    }

    // Parameterized constructor
    public CategoryUpdateDto(
            final Integer categoryId,
            final String categoryName) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
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

    @Override
    public String toString() {

        return "CategoryUpdateDto{"
                + "categoryId="
                + categoryId
                + ", categoryName='"
                + categoryName
                + '\''
                + '}';
    }
}