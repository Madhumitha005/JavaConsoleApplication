/*
 * OrderItemMapper.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.orderitem.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.orderitem.dto.OrderItemRequestDto;
import com.ecommerce.orderitem.dto.OrderItemResponseDto;
import com.ecommerce.orderitem.dto.OrderItemUpdateDto;
import com.ecommerce.orderitem.entity.OrderItem;

@Component
public class OrderItemMapper {

    public OrderItem toEntity(final OrderItemRequestDto dto) {

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderId(dto.getOrderId());
        orderItem.setProductId(dto.getProductId());
        orderItem.setQuantity(dto.getQuantity());

        return orderItem;
    }

    public OrderItem toEntity(final OrderItemUpdateDto dto) {

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(dto.getOrderItemId());
        orderItem.setQuantity(dto.getQuantity());

        return orderItem;
    }

    public OrderItemResponseDto toResponseDto(final OrderItem orderItem) {

        OrderItemResponseDto dto = new OrderItemResponseDto();

        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setOrderId(orderItem.getOrderId());
        dto.setProductId(orderItem.getProductId());
        dto.setQuantity(orderItem.getQuantity());

        return dto;
    }
}