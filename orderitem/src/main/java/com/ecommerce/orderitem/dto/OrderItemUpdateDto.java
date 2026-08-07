package com.ecommerce.orderitem.dto;

public class OrderItemUpdateDto {

    private Integer orderItemId;
    private Integer quantity;

    public Integer getOrderItemId() {

        return orderItemId;
    }

    public void setOrderItemId(final Integer orderItemId) {

        this.orderItemId = orderItemId;
    }

    public Integer getQuantity() {

        return quantity;
    }

    public void setQuantity(final Integer quantity) {

        this.quantity = quantity;
    }
}