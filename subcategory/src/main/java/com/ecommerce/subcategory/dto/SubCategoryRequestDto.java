
        /*
         * SubCategoryRequestDto.java
         *
         * Version 1.1
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
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object used for
 * subcategory creation requests.
 */
public class SubCategoryRequestDto {

    @NotBlank(
            message = "Subcategory name is required."
    )
    @Size(
            min = 3,
            max = 50,
            message = "Subcategory name must contain 3 to 50 characters."
    )
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z ]*$",
            message = "Subcategory name must contain only letters and spaces."
    )
    private String subCategoryName;

    @NotNull(
            message = "Category ID is required."
    )
    @Positive(
            message = "Category ID must be positive."
    )
    private Integer categoryId;

    public SubCategoryRequestDto() {
    }

    public SubCategoryRequestDto(
            final String subCategoryName,
            final Integer categoryId) {

        this.subCategoryName = subCategoryName;
        this.categoryId = categoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(final String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

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
