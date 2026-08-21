/*
 * CategoryUpdateDto.java
 *
 * Version 1.1
 *
 * August 21, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object used for
 * category update requests.
 */
public class CategoryUpdateDto {

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
            final String categoryName) {

        this.categoryName = categoryName;
    }

    // Returns the category name
    public String getCategoryName() {

        return categoryName;
    }

    // Sets the category name
    public void setCategoryName(
            final String categoryName) {

        this.categoryName = categoryName;
    }

    @Override
    public String toString() {

        return "CategoryUpdateDto{"
                + "categoryName='"
                + categoryName
                + '\''
                + '}';
    }
}