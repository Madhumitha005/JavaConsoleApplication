package com.ecommerce.util;

public final class IdGenerator {

    private static IdGenerator instance;

    private int userId;
    private int productId;
    private int cartId;
    private int orderId;

    private IdGenerator() {

        userId = 1;
        productId = 1;
        cartId = 1;
        orderId = 1;
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

    public int nextProductId() {
        return productId++;
    }

    public int nextCartId() {
        return cartId++;
    }

    public int nextOrderId() {
        return orderId++;
    }

    public void resetUserId(int startValue) {
        userId = startValue;
    }

    public void resetProductId(int startValue) {
        productId = startValue;
    }

    public void resetCartId(int startValue) {
        cartId = startValue;
    }

    public void resetOrderId(int startValue) {
        orderId = startValue;
    }
}