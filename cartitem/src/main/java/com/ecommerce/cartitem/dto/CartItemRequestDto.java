package com.ecommerce.cartitem.dto;

import jakarta.validation.constraints.Positive;

public class CartItemRequestDto {

    @Positive(message = "Invalid User Id")
    private int userId;

    @Positive(message = "Invalid Product Id")
    private int productId;

    @Positive(message = "Quantity must be greater than zero")
    private int quantity;

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(final int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}