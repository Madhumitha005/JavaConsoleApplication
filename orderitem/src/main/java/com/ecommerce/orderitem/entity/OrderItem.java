package com.ecommerce.orderitem.entity;

import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    private static final long serialVERSIONID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    @Positive(
            message = "Invalid Order Item ID.",
            groups = UpdateGroup.class
    )
    private int orderItemId;

    @Column(name = "order_id", nullable = false)
    @Positive(
            message = "Invalid Order ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int orderId;

    @Column(name = "product_id", nullable = false)
    @Positive(
            message = "Invalid Product ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int productId;

    @Column(name = "quantity", nullable = false)
    @Positive(
            message = "Quantity must be greater than zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(
            final int orderItemId,
            final int orderId,
            final int productId,
            final int quantity) {

        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(final int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(final int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(final int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
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
                + '}';
    }
}