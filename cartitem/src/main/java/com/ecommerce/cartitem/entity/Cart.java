/*
 * Cart.java
 *
 * Version 1.3
 *
 * July 25, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.

package com.ecommerce.cartitem.entity;

import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.constraints.Positive;

public class Cart {

    @Positive(
            message = "Invalid Cart Id",
            groups = UpdateGroup.class
    )
    private int cartId;

    @Positive(
            message = "Invalid User Id",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int userId;

    // Default Constructor
    public Cart() {
    }

    // Parameterized Constructor
    public Cart(final int cartId, final int userId) {

        this.cartId = cartId;
        this.userId = userId;
    }

    // Get Cart Id
    public int getCartId() {

        return cartId;
    }

    // Set Cart Id
    public void setCartId(final int cartId) {

        this.cartId = cartId;
    }

    // Get User Id
    public int getUserId() {

        return userId;
    }

    // Set User Id
    public void setUserId(final int userId) {

        this.userId = userId;
    }

    @Override
    public String toString() {

        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                '}';
    }
}

 */
