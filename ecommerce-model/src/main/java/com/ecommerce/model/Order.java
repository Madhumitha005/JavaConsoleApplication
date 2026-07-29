/*
 * Order.java
 *
 * Version 1.3
 *
 * July 24, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.model;

import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import com.ecommerce.common.enums.OrderStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class Order {

    // Order ID
    @Positive(
            message = "Invalid Order ID.",
            groups = UpdateGroup.class
    )
    private int orderId;

    // User ID
    @Positive(
            message = "Invalid User ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int userId;

    // Customer Name
    @NotBlank(
            message = "Customer Name cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            min = 2,
            max = 50,
            message =
                    "Customer Name must be between "
                            + "2 and 50 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message =
                    "Customer Name must contain "
                            + "only letters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String customerName;

    // Phone
    @NotBlank(
            message = "Phone Number cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Invalid Phone Number.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String phone;

    // Address
    @NotBlank(
            message = "Address cannot be empty.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Size(
            min = 5,
            max = 200,
            message =
                    "Address must be between "
                            + "5 and 200 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String address;

    // Total Amount
    @PositiveOrZero(
            message =
                    "Total Amount cannot be negative.",
            groups = CreateGroup.class
    )
    @Positive(
            message =
                    "Total Amount must be greater than zero.",
            groups = UpdateGroup.class
    )
    private double totalAmount;

    private OrderStatus orderStatus;

    // Default Constructor
    public Order() {
    }

    // Parametarized Constructor
    public Order(
            final int orderId,
            final int userId,
            final String customerName,
            final String phone,
            final String address,
            final double totalAmount) {

        this.orderId = orderId;
        this.userId = userId;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.totalAmount = totalAmount;
    }

   // Get order Id
    public int getOrderId() {

        return orderId;
    }

    // Set order Id
    public void setOrderId(final int orderId) {

        this.orderId = orderId;
    }

    // Get User Id
    public int getUserId() {

        return userId;
    }

    // Set User Id
    public void setUserId(final int userId) {

        this.userId = userId;
    }

    // Get Customer Name
    public String getCustomerName() {

        return customerName;
    }

    // Set Customer Name
    public void setCustomerName(final String customerName) {

        this.customerName = customerName;
    }

    // Get Phone
    public String getPhone() {

        return phone;
    }

    // Set phone
    public void setPhone(final String phone) {

        this.phone = phone;
    }

    // Get Address
    public String getAddress() {

        return address;
    }

    // Set Address
    public void setAddress(final String address) {

        this.address = address;
    }

    // Get Total Amount
    public double getTotalAmount() {

        return totalAmount;
    }

    // Set Total Amount
    public void setTotalAmount(final double totalAmount) {

        this.totalAmount = totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }


    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {

        return "Order{"
                + "orderId=" + orderId
                + ", userId=" + userId
                + ", customerName='"
                + customerName + '\''
                + ", phone='"
                + phone + '\''
                + ", address='"
                + address + '\''
                + ", totalAmount="
                + totalAmount
                + '}';
    }
}