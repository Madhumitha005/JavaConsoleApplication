/*
 * Payment.java
 *
 * Version 1.3
 *
 * July 25, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.model;

import com.ecommerce.common.enums.PaymentMethod;
import com.ecommerce.common.enums.PaymentStatus;
import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Payment {

    // Payment Id
    @Positive(
            message = "Invalid Payment ID.",
            groups = UpdateGroup.class
    )
    private int paymentId;

    // Order Id
    @Positive(
            message = "Invalid Order ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int orderId;

    // Amount
    @Positive(
            message =
                    "Amount must be greater than zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private double amount;

    // Payment Method
    @NotNull(
            message =
                    "Payment Method is required.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private PaymentMethod paymentMethod;

    // Payment status
    @NotNull(
            message =
                    "Payment Status is required.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private PaymentStatus paymentStatus;

    // Transaction Id
    @Size(
            max = 50,
            message =
                    "Transaction ID must not exceed "
                            + "50 characters.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private String transactionId;

    // Default Constructor
    public Payment() {
    }

    // Parametarized Constructor
    public Payment(
            final int paymentId,
            final int orderId,
            final double amount,
            final PaymentMethod paymentMethod,
            final PaymentStatus paymentStatus,
            final String transactionId) {

        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
    }

    public int getPaymentId() {

        return paymentId;
    }

    // Set Payment Id
    public void setPaymentId(final int paymentId) {

        this.paymentId = paymentId;
    }

    // Get Order Id
    public int getOrderId() {

        return orderId;
    }

    // Set OrderId
    public void setOrderId(final int orderId) {

        this.orderId = orderId;
    }

    // Get Amount
    public double getAmount() {

        return amount;
    }

    // Set Amount
    public void setAmount(final double amount) {

        this.amount = amount;
    }

    // Get Payment Method
    public PaymentMethod getPaymentMethod() {

        return paymentMethod;
    }

   // Set Payment Method
    public void setPaymentMethod(final PaymentMethod paymentMethod) {

        this.paymentMethod = paymentMethod;
    }

    // Get Payment Status
    public PaymentStatus getPaymentStatus() {

        return paymentStatus;
    }

    // Set Payment Status
    public void setPaymentStatus(final PaymentStatus paymentStatus) {

        this.paymentStatus = paymentStatus;
    }

    // Get Transaction Id
    public String getTransactionId() {

        return transactionId;
    }

    // Set Transaction Id
    public void setTransactionId(final String transactionId) {

        this.transactionId = transactionId;
    }

    @Override
    public String toString() {

        return "Payment{"
                + "paymentId="
                + paymentId
                + ", orderId="
                + orderId
                + ", amount="
                + amount
                + ", paymentMethod="
                + paymentMethod
                + ", paymentStatus="
                + paymentStatus
                + ", transactionId='"
                + transactionId
                + '}';
    }
}
