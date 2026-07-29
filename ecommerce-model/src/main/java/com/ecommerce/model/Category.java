package com.ecommerce.model;

import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Category {

    // Catgeory Id
    @Positive(
            message = "Invalid Category ID.",
            groups = UpdateGroup.class
    )
    private int categoryId;

    // Category Name
    @NotBlank(
            message = "Category Name cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            min = 3,
            max = 50,
            message = "Category Name must be between 3 and 50 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z ]*$",
            message =
                    "Category Name must contain only "
                            + "letters and spaces.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String categoryName;

    // Default Category
    public Category() {
    }

    // Parametarized Contructor
    public Category(final int categoryId, final String categoryName) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Category ID
    public int getCategoryId() {

        return categoryId;
    }

    // Set Category Id
    public void setCategoryId(final int categoryId) {

        this.categoryId = categoryId;
    }

    // Get Category Name
    public String getCategoryName() {

        return categoryName;
    }

    // Set Category Name
    public void setCategoryName(final String categoryName) {

        this.categoryName = categoryName;
    }

    @Override
    public String toString() {

        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" +
                categoryName + '\'' +
                '}';
    }
}
