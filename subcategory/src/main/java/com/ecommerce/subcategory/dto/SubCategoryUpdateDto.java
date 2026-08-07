/*
 * SubCategoryUpdateDto.java
 *
 * Version 1.0
 *
 * July 31, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.subcategory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object used for
 * subcategory update requests.
 */
public class SubCategoryUpdateDto {

    // Subcategory id
    @NotNull(
            message = "Subcategory ID is required."
    )
    private Integer subCategoryId;

    // Subcategory name
    @NotBlank(
            message = "Subcategory name is required."
    )
    @Size(
            min = 3,
            max = 50,
            message =
                    "Subcategory name must contain "
                            + "3 to 50 characters."
    )
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z ]*$",
            message =
                    "Subcategory name must contain "
                            + "only letters."
    )
    private String subCategoryName;

    // Category id
    @NotNull(
            message = "Category ID is required."
    )
    private Integer categoryId;

    // Default constructor
    public SubCategoryUpdateDto() {
    }

    // Parameterized constructor
    public SubCategoryUpdateDto(
            final Integer subCategoryId,
            final String subCategoryName,
            final Integer categoryId) {

        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.categoryId = categoryId;
    }

    // Returns the subcategory id
    public Integer getSubCategoryId() {

        return subCategoryId;
    }

    // Sets the subcategory id
    public void setSubCategoryId(final Integer subCategoryId) {

        this.subCategoryId = subCategoryId;
    }

    // Returns the subcategory name
    public String getSubCategoryName() {

        return subCategoryName;
    }

    // Sets the subcategory name
    public void setSubCategoryName(final String subCategoryName) {

        this.subCategoryName = subCategoryName;
    }

    // Returns the category id
    public Integer getCategoryId() {

        return categoryId;
    }

    // Sets the category id
    public void setCategoryId(final Integer categoryId) {

        this.categoryId = categoryId;
    }

    @Override
    public String toString() {

        return "SubCategoryUpdateDto{"
                + "subCategoryId="
                + subCategoryId
                + ", subCategoryName='"
                + subCategoryName
                + '\''
                + ", categoryId="
                + categoryId
                + '}';
    }
}