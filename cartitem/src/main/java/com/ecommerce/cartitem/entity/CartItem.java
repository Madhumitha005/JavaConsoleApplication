/*
 * CartItem.java
 *
 * Version 2.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.cartitem.entity;

import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.constraints.Positive;

public class CartItem {

    @Positive(
            message = "Invalid Cart Item ID.",
            groups = UpdateGroup.class
    )
    private int cartItemId;

    @Positive(
            message = "Invalid User ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int userId;

    @Positive(
            message = "Invalid Product ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int productId;

    @Positive(
            message = "Quantity must be greater than zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int quantity;

    public CartItem() {
    }

    public CartItem(
            final int cartItemId,
            final int userId,
            final int productId,
            final int quantity) {

        this.cartItemId = cartItemId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

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

    @Override
    public String toString() {

        return "CartItem{"
                + "cartItemId="
                + cartItemId
                + ", userId="
                + userId
                + ", productId="
                + productId
                + ", quantity="
                + quantity
                + '}';
    }
}