package com.ecommerce.model;

import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.constraints.Positive;

public class OrderItem {

    // Order Item Id
    @Positive(
            message = "Invalid Order Item ID.",
            groups = UpdateGroup.class
    )
    private int orderItemId;

    // Order Id
    @Positive(
            message = "Invalid Order ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int orderId;

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
            message =
                    "Quantity must be greater than zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int quantity;

    // Price
    @Positive(
            message =
                    "Price must be greater than zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private double price;

    // Default Constructor
    public OrderItem() {
    }

    // Parametarized Constructor
    public OrderItem(
            final int orderItemId,
            final int orderId,
            final int productId,
            final int quantity,
            final double price) {

        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Get Order Item Id
    public int getOrderItemId() {

        return orderItemId;
    }

    // Set Order Item Id
    public void setOrderItemId(final int orderItemId) {

        this.orderItemId = orderItemId;
    }

    // Get Order Id
    public int getOrderId() {

        return orderId;
    }

    // Set Order Id
    public void setOrderId(final int orderId) {

        this.orderId = orderId;
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

    // Get Price
    public double getPrice() {

        return price;
    }

    // Set Price
    public void setPrice(final double price) {

        this.price = price;
    }

    @Override
    public String toString() {

        return "OrderItem{"
                + "orderItemId="
                + orderItemId
                + ", orderId="
                + orderId
                + ", productId="
                + productId
                + ", quantity="
                + quantity
                + ", price="
                + price
                + '}';
    }
}
