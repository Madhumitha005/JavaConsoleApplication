/*
 * OrderItemController.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.orderitem.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.orderitem.entity.OrderItem;
import com.ecommerce.orderitem.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(final OrderItemService orderItemService) {

        this.orderItemService = Objects.requireNonNull(orderItemService, "OrderItemService cannot be null.");
    }

    @PostMapping
    public ResponseEntity<Boolean> addOrderItem(
            @RequestBody final OrderItem orderItem) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderItemService.addOrderItem(orderItem));
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(
            @PathVariable final Integer orderItemId) {

        return ResponseEntity.ok(orderItemService.getOrderItemById(orderItemId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Collection<OrderItem>> getOrderItemsByOrderId(
            @PathVariable final Integer orderId) {

        return ResponseEntity.ok(orderItemService.getOrderItemsByOrderId(orderId));
    }

    @GetMapping
    public ResponseEntity<Collection<OrderItem>> viewOrderItems() {

        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @PutMapping
    public ResponseEntity<Boolean> updateOrderItem(
            @RequestBody final OrderItem orderItem) {

        return ResponseEntity.ok(orderItemService.updateOrderItem(orderItem));
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Boolean> deleteOrderItem(
            @PathVariable final Integer orderItemId) {

        return ResponseEntity.ok(orderItemService.deleteOrderItem(orderItemId));
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Boolean> deleteOrderItemsByOrderId(
            @PathVariable final Integer orderId) {

        return ResponseEntity.ok(orderItemService.deleteOrderItemsByOrderId(orderId));
    }
}