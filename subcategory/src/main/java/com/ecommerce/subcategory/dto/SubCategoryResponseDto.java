/*
 * SubCategoryResponseDto.java
 *
 * Version 1.0
 *
 * July 31, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.subcategory.dto;

/**
 * Data transfer object used for
 * returning subcategory details.
 */
public class SubCategoryResponseDto {

    // Subcategory id
    private Integer subCategoryId;

    // Subcategory name
    private String subCategoryName;

    // Category id
    private Integer categoryId;

    // Default constructor
    public SubCategoryResponseDto() {
    }

    // Parameterized constructor
    public SubCategoryResponseDto(
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

        return "SubCategoryResponseDto{"
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