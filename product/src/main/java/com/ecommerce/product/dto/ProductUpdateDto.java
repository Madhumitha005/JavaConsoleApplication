package com.ecommerce.product.dto;

import com.ecommerce.common.enums.ProductStatus;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductUpdateDto {

    @NotNull(
            message = "Product ID is required."
    )
    private Integer productId;

    @NotBlank(
            message = "Product name is required."
    )
    @Size(
            min = 3,
            max = 100,
            message = "Product name must contain 3 to 100 characters."
    )
    @Pattern(
            regexp = "^[A-Za-z0-9 ]+$",
            message = "Product name can contain only letters, numbers and spaces."
    )
    private String productName;

    @NotNull(
            message = "Price is required."
    )
    @Positive(
            message = "Price must be greater than zero."
    )
    private Double price;

    @NotNull(
            message = "Quantity is required."
    )
    @Positive(
            message = "Quantity must be greater than zero."
    )
    private Integer quantity;

    @DecimalMin(
            value = "0.0",
            message = "Discount cannot be negative."
    )
    @DecimalMax(
            value = "100.0",
            message = "Discount cannot exceed 100."
    )
    private Double discount;

    @DecimalMin(
            value = "0.0",
            message = "Tax cannot be negative."
    )
    @DecimalMax(
            value = "100.0",
            message = "Tax cannot exceed 100."
    )
    private Double tax;

    @NotNull(
            message = "Seller ID is required."
    )
    @Positive(
            message = "Seller ID must be greater than zero."
    )
    private Integer sellerId;

    @NotNull(
            message = "Subcategory ID is required."
    )
    private Integer subCategoryId;

    @NotNull(
            message = "Product status is required."
    )
    private ProductStatus productStatus;

    public ProductUpdateDto() {
    }

    public Integer getProductId() {

        return productId;
    }

    public void setProductId(final Integer productId) {

        this.productId = productId;
    }

    public String getProductName() {

        return productName;
    }

    public void setProductName(final String productName) {

        this.productName = productName;
    }

    public Double getPrice() {

        return price;
    }

    public void setPrice(final Double price) {

        this.price = price;
    }

    public Integer getQuantity() {

        return quantity;
    }

    public void setQuantity(final Integer quantity) {

        this.quantity = quantity;
    }

    public Double getDiscount() {

        return discount;
    }

    public void setDiscount(final Double discount) {

        this.discount = discount;
    }

    public Double getTax() {

        return tax;
    }

    public void setTax(final Double tax) {

        this.tax = tax;
    }

    public Integer getSellerId() {

        return sellerId;
    }

    public void setSellerId(
            final Integer sellerId) {

        this.sellerId = sellerId;
    }

    public Integer getSubCategoryId() {

        return subCategoryId;
    }

    public void setSubCategoryId(final Integer subCategoryId) {

        this.subCategoryId = subCategoryId;
    }

    public ProductStatus getProductStatus() {

        return productStatus;
    }

    public void setProductStatus(final ProductStatus productStatus) {

        this.productStatus = productStatus;
    }
}