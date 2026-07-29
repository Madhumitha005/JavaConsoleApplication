/*
 * IdGenerator.java
 *
 * Version 1.0
 *
 * July 24, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.common.util;

/**
 * Generates unique IDs for all entities in the
 * ecommerce system.
 *
 * This class follows the Singleton design pattern
 * to ensure that only one instance of the ID
 * generator exists throughout the application.
 *
 */
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

    /**
     * Creates an IdGenerator.
     */
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
    }

    public static IdGenerator getInstance() {

        if (instance == null) {
            instance = new IdGenerator();
        }

        return instance;
    }

    public int nextUserId() {
        return userId++;
    }

    public int nextCategoryId() {
        return categoryId++;
    }

    public int nextSubCategoryId() {
        return subCategoryId++;
    }

    public int nextProductId() {
        return productId++;
    }

    public int nextCartId() {
        return cartId++;
    }

    public int nextCartItemId() {
        return cartItemId++;
    }

    public int nextOrderId() {
        return orderId++;
    }

    public int nextOrderItemId() {
        return orderItemId++;
    }

    public int nextPaymentId() {
        return paymentId++;
    }
}
