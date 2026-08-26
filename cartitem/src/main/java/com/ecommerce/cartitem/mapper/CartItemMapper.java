/*
 * CartItemMapper.java
 *
 * Version 1.1
 *
 * August 21, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.cartitem.mapper;

import org.springframework.stereotype.Component;
import com.ecommerce.cartitem.dto.CartItemRequestDto;
import com.ecommerce.cartitem.dto.CartItemResponseDto;
import com.ecommerce.cartitem.dto.CartItemUpdateDto;
import com.ecommerce.cartitem.entity.CartItem;

@Component
public class CartItemMapper {

    public CartItem toEntity(final CartItemRequestDto dto) {

        if (dto == null) {
            return null;
        }
        CartItem cartItem = new CartItem();
        cartItem.setUserId(dto.getUserId());
        cartItem.setProductId(dto.getProductId());
        cartItem.setQuantity(dto.getQuantity());
        return cartItem;
    }

    public CartItem toEntity(final CartItemUpdateDto dto) {

        if (dto == null) {
            return null;
        }

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(dto.getCartItemId());
        cartItem.setUserId(dto.getUserId());
        cartItem.setProductId(dto.getProductId());
        cartItem.setQuantity(dto.getQuantity());
        return cartItem;
    }

    public CartItemResponseDto toResponseDto(
            final CartItem cartItem) {

        if (cartItem == null) {
            return null;
        }
        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setUserId(cartItem.getUserId());
        dto.setProductId(cartItem.getProductId());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }
}