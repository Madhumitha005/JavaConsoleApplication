package com.ecommerce.cartitem.dto;

public class CartItemResponseDto {

    private int cartItemId;
    private int userId;
    private int productId;
    private int quantity;

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(final int cartItemId) {

        this.cartItemId = cartItemId;
    }

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