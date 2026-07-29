package com.ecommerce.model;

import com.ecommerce.common.enums.ProductStatus;
import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class Product {

    // Product Id
    @Positive(
            message = "Invalid Product ID.",
            groups = UpdateGroup.class
    )
    private int productId;

    // Product Name
    @NotBlank(
            message = "Product Name cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            min = 3,
            max = 100,
            message =
                    "Product Name must be between "
                            + "3 and 100 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^[A-Za-z0-9 ]+$",
            message =
                    "Product Name can contain only "
                            + "letters, numbers and spaces.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String productName;

    // Price
    @Positive(
            message = "Price must be greater than zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private double price;

    // Quantity
    @Positive(
            message =
                    "Stock Quantity must be "
                            + "greater than zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int quantity;

    // Discount Percentage
    @DecimalMin(
            value = "0.0",
            message = "Discount Percentage cannot Be less than Zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @DecimalMax(
            value ="100.0",
            message = "Discount Percentage cannot Be greater than 100.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private double discountPercentage;

    // Category
    @NotNull(
            message = "Category cannot be null.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Valid
    private Category category;

    // Sub Category
    @NotNull(
            message = "Sub Category cannot be null.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Valid
    private SubCategory subCategory;

    // Product Status
    @NotNull(
            message = "Product Status is required.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private ProductStatus productStatus;

    // Default Constructor
    public Product() {
    }

    // Parametarized Constructor
    public Product(
            final int productId,
            final String productName,
            final double price,
            final int quantity,
            final double discountPercentage,
            final Category category,
            final SubCategory subCategory,
            final ProductStatus productStatus) {

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this. discountPercentage = discountPercentage;
        this.category = category;
        this.subCategory = subCategory;
        this.productStatus = productStatus;
    }

    // Get Product Id
    public int getProductId() {

        return productId;
    }

    // Set Product Id
    public void setProductId(final int productId) {
        this.productId = productId;
    }

    // Get Product Name
    public String getProductName() {

        return productName;
    }

    // Set Product Name
    public void setProductName(final String productName) {
        this.productName = productName;
    }

    // Get Price
    public double getPrice() {

        return price;
    }

    // Set Price
    public void setPrice(final double price) {
        this.price = price;
    }

    // Get Quantity
    public int getQuantity() {

        return quantity;
    }

    // Set Quantity
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    // Get Discount Percentage
    public double getDiscountPercentage() {

        return discountPercentage;
    }

    // Set Discount Percentage

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    // Get Category
    public Category getCategory() {

        return category;
    }

    // Set Category
    public void setCategory(final Category category) {
        this.category = category;
    }

    // Get SubCategory
    public SubCategory getSubCategory() {

        return subCategory;
    }

    //Set Subcategory
    public void setSubCategory(final SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    // Get Product Status
    public ProductStatus getProductStatus() {

        return productStatus;
    }

    // Set Product Status
    public void setProductStatus(final ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {

        return "Product{"
                + "productId="
                + productId
                + ", productName='"
                + productName
                + ", price="
                + price
                + ", discountPercentage="
                + discountPercentage
                + ", quantity="
                + quantity
                + ", category="
                + category
                + ", subCategory="
                + subCategory
                + ", productStatus="
                + productStatus
                + '}';
    }
}
