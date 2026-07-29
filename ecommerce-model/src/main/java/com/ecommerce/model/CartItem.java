package com.ecommerce.model;

import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.constraints.Positive;

public class CartItem {

    // Cart Item ID
    @Positive(
            message = "Invalid Cart Item ID.",
            groups = UpdateGroup.class
    )
    private int cartItemId;

    // Cart Id
    @Positive(
            message = "Invalid Cart ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int cartId;

    // Product Id
    @Positive(
            message = "Invalid Product ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int productId;

    // Quantity
    @Positive(
            message = "Quantity must be greater than zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int quantity;

    // Default Constructor
    public CartItem() {
    }

    // Parametarized Constructor
    public CartItem(
            final int cartItemId,
            final int cartId,
            final int productId,
            final int quantity) {

        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Get Cart Item Id
    public int getCartItemId() {

        return cartItemId;
    }

    // Set Cart Item Id
    public void setCartItemId(final int cartItemId) {

        this.cartItemId = cartItemId;
    }

    // Get Cart Id
    public int getCartId() {

        return cartId;
    }

    // Set Cart Id
    public void setCartId(final int cartId) {

        this.cartId = cartId;
    }

    // Get Product Id
    public int getProductId() {

        return productId;
    }

    // Set Product Id
    public void setProductId(final int productId) {

        this.productId = productId;
    }

    // Get Quantity
    public int getQuantity() {

        return quantity;
    }

    // Set Quantity
    public void setQuantity(final int quantity) {

        this.quantity = quantity;
    }

    @Override
    public String toString() {

        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", cartId=" + cartId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
