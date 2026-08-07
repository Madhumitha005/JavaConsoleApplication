/*
 * OrderController.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.order.controller;

import java.util.Collection;
import java.util.Objects;
import java.util.ArrayList;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order.dto.OrderRequestDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.dto.OrderUpdateDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.orderitem.dto.OrderItemRequestDto;
import com.ecommerce.orderitem.entity.OrderItem;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.service.OrderService;

@RestController
@Validated
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(
            final OrderService orderService,
            final OrderMapper orderMapper) {

        this.orderService = Objects.requireNonNull(orderService, "OrderService cannot be null.");
        this.orderMapper = Objects.requireNonNull(orderMapper, "OrderMapper cannot be null.");
    }

    // Places an order
    @PostMapping
    public ResponseEntity<Boolean> placeOrder(
            @Valid
            @RequestBody
            final OrderRequestDto requestDto) {

        Order order = orderMapper.toEntity(requestDto);

        Collection<OrderItem> orderItems = new ArrayList<>();
        if (requestDto.getItems() != null) {
            for (OrderItemRequestDto itemDto : requestDto.getItems()) {
                OrderItem item = new OrderItem();
                item.setProductId(itemDto.getProductId());
                item.setQuantity(itemDto.getQuantity());
                orderItems.add(item);
            }
        }

        boolean result = orderService.placeOrder(order, orderItems);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // Returns all orders
    @GetMapping
    public ResponseEntity<Collection<OrderResponseDto>> viewOrders() {

        Collection<OrderResponseDto> orders = orderService.findAll()
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(orders);
    }

    // Returns an order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable
            final Integer orderId) {

        Order order = orderService.findById(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderMapper.toResponseDto(order));
    }

    // Returns orders by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Collection<OrderResponseDto>> getOrdersByUserId(
            @PathVariable
            final Integer userId) {

        Collection<OrderResponseDto> orders = orderService.findByUserId(userId)
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(orders);
    }

    // Updates an order
    @PutMapping("/{orderId}")
    public ResponseEntity<Boolean> updateOrder(
            @PathVariable
            final Integer orderId,
            @Valid
            @RequestBody
            final OrderUpdateDto updateDto) {

        Order order = orderMapper.toEntity(updateDto);
        order.setOrderId(orderId);

        return ResponseEntity.ok(orderService.update(order));
    }

    // Deletes an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Boolean> deleteOrder(
            @PathVariable
            final Integer orderId) {

        return ResponseEntity.ok(orderService.delete(orderId));
    }

    // Returns orders by seller ID
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Collection<OrderResponseDto>> getOrdersBySellerId(
            @PathVariable
            final Integer sellerId) {

        Collection<OrderResponseDto> orders =
                orderService.findBySellerId(sellerId)
                        .stream()
                        .map(orderMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(orders);
    }
}
