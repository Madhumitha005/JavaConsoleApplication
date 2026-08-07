/*
 * SubCategoryRequestDto.java
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
 * subcategory creation requests.
 */
public class SubCategoryRequestDto {

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
    public SubCategoryRequestDto() {
    }

    // Parameterized constructor
    public SubCategoryRequestDto(
            final String subCategoryName,
            final Integer categoryId) {

        this.subCategoryName = subCategoryName;
        this.categoryId = categoryId;
    }

    // Returns subcategory name
    public String getSubCategoryName() {

        return subCategoryName;
    }

    // Sets subcategory name
    public void setSubCategoryName(final String subCategoryName) {

        this.subCategoryName = subCategoryName;
    }

    // Returns category id
    public Integer getCategoryId() {

        return categoryId;
    }

    // Sets category id
    public void setCategoryId(final Integer categoryId) {

        this.categoryId = categoryId;
    }

    @Override
    public String toString() {

        return "SubCategoryRequestDto{"
                + "subCategoryName='"
                + subCategoryName
                + '\''
                + ", categoryId="
                + categoryId
                + '}';
    }
}