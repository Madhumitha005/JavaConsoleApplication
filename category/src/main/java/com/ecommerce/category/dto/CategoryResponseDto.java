/*
 * CategoryResponseDto.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.dto;

/**
 * Data transfer object used for
 * category response operations.
 */
public class CategoryResponseDto {

    // Category id
    private Integer categoryId;

    // Category name
    private String categoryName;

    // Default constructor
    public CategoryResponseDto() {
    }

    // Parameterized constructor
    public CategoryResponseDto(
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

        return "CategoryResponseDto{"
                + "categoryId="
                + categoryId
                + ", categoryName='"
                + categoryName
                + '\''
                + '}';
    }
}