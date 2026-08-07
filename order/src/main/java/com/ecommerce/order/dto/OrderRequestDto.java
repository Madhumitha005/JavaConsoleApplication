package com.ecommerce.order.dto;

import java.util.List;

import com.ecommerce.common.enums.PaymentMethod;
import com.ecommerce.orderitem.dto.OrderItemRequestDto;

public class OrderRequestDto {

    private Integer userId;
    private Integer sellerId;
    private String customerName;
    private String phone;
    private String address;
    private PaymentMethod paymentMethod;
    private Double totalAmount;
    private List<OrderItemRequestDto> items;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(final Integer sellerId) {this.sellerId = sellerId;}

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItemRequestDto> getItems() {
        return items;
    }

    public void setItems(
            final List<OrderItemRequestDto> items) {

        this.items = items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(final Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}