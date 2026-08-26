/*
 * CartItem.java
 *
 * Version 3.0
 *
 * August 21, 2026
 */
package com.ecommerce.cartitem.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;

@Entity
@Table(name = "cart_item")
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(
            message = "Invalid Cart Item ID.",
            groups = UpdateGroup.class
    )
    @Column(name = "id")
    private Integer cartItemId;

    @Positive(
            message = "Invalid User ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Positive(
            message = "Invalid Product ID.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Positive(
            message = "Quantity must be greater than zero.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(
            final Integer cartItemId,
            final Integer userId,
            final Integer productId,
            final Integer quantity) {

        this.cartItemId = cartItemId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(final Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
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

    @Override
    public String toString() {

        return "CartItem{"
                + "cartItemId="
                + cartItemId
                + ", userId="
                + userId
                + ", productId="
                + productId
                + ", quantity="
                + quantity
                + '}';
    }
}