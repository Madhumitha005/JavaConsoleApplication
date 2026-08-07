package com.ecommerce.orderitem.dto;

public class OrderItemResponseDto {

    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;

    public Integer getOrderItemId() {

        return orderItemId;
    }

    public void setOrderItemId(final Integer orderItemId) {

        this.orderItemId = orderItemId;
    }

    public Integer getOrderId() {

        return orderId;
    }

    public void setOrderId(final Integer orderId) {

        this.orderId = orderId;
    }

    public Integer getProductId() {

        return productId;
    }

    public void setProductId(final Integer productId) {

        this.productId = productId;
    }

    public Integer getQuantity() {

        return quantity;
    }

    public void setQuantity(final Integer quantity) {

        this.quantity = quantity;
    }
}