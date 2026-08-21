package com.ecommerce.order.dto;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.enums.PaymentStatus;

import jakarta.validation.constraints.NotNull;

public class OrderUpdateDto {

    @NotNull
    private OrderStatus orderStatus;

    @NotNull
    private PaymentStatus paymentStatus;

    public OrderUpdateDto() {
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}