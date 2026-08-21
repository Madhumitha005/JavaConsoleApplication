/*
 * OrderMapper.java
 *
 * Version 2.0
 *
 * August 05, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.order.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.enums.PaymentMethod;
import com.ecommerce.common.enums.PaymentStatus;
import com.ecommerce.order.dto.OrderRequestDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.dto.OrderUpdateDto;
import com.ecommerce.order.entity.Order;

@Component
public class OrderMapper {

    public Order toEntity(final OrderRequestDto requestDto) {

        if (requestDto == null) {

            return null;
        }

        Order order = new Order();
        order.setUserId(requestDto.getUserId());
        order.setSellerId(requestDto.getSellerId());
        order.setCustomerName(requestDto.getCustomerName());
        order.setPhone(requestDto.getPhone());
        order.setAddress(requestDto.getAddress());
        order.setTotalAmount(requestDto.getTotalAmount());

        if (requestDto.getPaymentMethod() != null) {

            order.setPaymentMethod(requestDto.getPaymentMethod());
        }
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTransactionId(UUID.randomUUID().toString());
        return order;
    }

    public OrderResponseDto toResponseDto(final Order order) {

        if (order == null) {

            return null;
        }

        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setUserId(order.getUserId());
        responseDto.setSellerId(order.getSellerId());
        responseDto.setCustomerName(order.getCustomerName());
        responseDto.setPhone(order.getPhone());
        responseDto.setAddress(order.getAddress());
        responseDto.setTotalAmount(order.getTotalAmount());

        if (order.getPaymentMethod() != null) {

            responseDto.setPaymentMethod(order.getPaymentMethod());
        }
        responseDto.setPaymentStatus(order.getPaymentStatus());
        responseDto.setOrderStatus(order.getOrderStatus());
        responseDto.setTransactionId(order.getTransactionId());
        return responseDto;
    }

    public Order toEntity(final OrderUpdateDto updateDto) {

        if (updateDto == null) {

            return null;
        }

        Order order = new Order();
        order.setPaymentStatus(updateDto.getPaymentStatus());
        order.setOrderStatus(updateDto.getOrderStatus());

        return order;
    }
}