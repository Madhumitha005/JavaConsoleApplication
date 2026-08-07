package com.ecommerce.cartitem.dto;

import jakarta.validation.constraints.Positive;

public class CartItemUpdateDto {

    @Positive(message = "Invalid Cart Item Id")
    private int cartItemId;

    @Positive(message = "Invalid Product Id")
    private int productId;

    @Positive(message = "Quantity must be greater than zero")
    private int quantity;

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(final int cartItemId) {
        this.cartItemId = cartItemId;
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