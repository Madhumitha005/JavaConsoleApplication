package com.ecommerce.common.util;

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

    public void resetUserId(final int value) {
        userId = value;
    }

    public void resetCategoryId(final int value) {
        categoryId = value;
    }

    public void resetSubCategoryId(final int value) {
        subCategoryId = value;
    }

    public void resetProductId(final int value) {
        productId = value;
    }

    public void resetCartId(final int value) {
        cartId = value;
    }

    public void resetCartItemId(final int value) {
        cartItemId = value;
    }

    public void resetOrderId(final int value) {
        orderId = value;
    }

    public void resetOrderItemId(final int value) {
        orderItemId = value;
    }

    public void resetPaymentId(final int value) {
        paymentId = value;
    }
}