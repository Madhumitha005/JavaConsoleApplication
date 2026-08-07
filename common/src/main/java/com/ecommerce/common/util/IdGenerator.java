/*
 * IdGenerator.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.common.util;

// Generates unique identifiers for all entities
public final class IdGenerator {

    private static IdGenerator instance;

    private int userId;
    private int categoryId;
    private int subCategoryId;
    private int productId;
    private int cartId;
    private int cartItemId;
    private int orderId;
    private int orderItemId;
    private int paymentId;
    private int reviewId;

    // Creates an IdGenerator object
    private IdGenerator() {

        userId = 1;
        categoryId = 1;
        subCategoryId = 1;
        productId = 1;
        cartId = 1;
        cartItemId = 1;
        orderId = 1;
        orderItemId = 1;
        paymentId = 1;
        reviewId = 1;
    }

    // Returns the singleton instance
    public static synchronized IdGenerator getInstance() {

        if (instance == null) {
            instance = new IdGenerator();
        }

        return instance;
    }

    // Generates the next user id
    public int nextUserId() {

        return userId++;
    }

    // Generates the next category id
    public int nextCategoryId() {

        return categoryId++;
    }

    // Generates the next subcategory id
    public int nextSubCategoryId() {

        return subCategoryId++;
    }

    // Generates the next product id
    public int nextProductId() {

        return productId++;
    }

    // Generates the next cart id
    public int nextCartId() {

        return cartId++;
    }

    // Generates the next cart item id
    public int nextCartItemId() {

        return cartItemId++;
    }

    // Generates the next order id
    public int nextOrderId() {

        return orderId++;
    }

    // Generates the next order item id
    public int nextOrderItemId() {

        return orderItemId++;
    }

    // Generates the next payment id
    public int nextPaymentId() {

        return paymentId++;
    }

    // Generates the next review id
    public int nextReviewId() {
        return ++reviewId;
    }
}