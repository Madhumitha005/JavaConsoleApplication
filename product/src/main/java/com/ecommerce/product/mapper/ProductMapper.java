/*
 * ProductMapper.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.product.mapper;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import com.ecommerce.user.entity.User;
import com.ecommerce.common.enums.ProductStatus;
import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.dto.ProductUpdateDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.subcategory.entity.SubCategory;

@Component
public class ProductMapper {

    public Product toEntity(final ProductRequestDto requestDto) {

        if (requestDto == null) {
            return null;
        }

        Product product = new Product();

        User seller = new User();
        seller.setId(requestDto.getSellerId());

        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryId(requestDto.getSubCategoryId());

        product.setName(requestDto.getProductName());
        product.setPrice(requestDto.getPrice());
        product.setQuantity(requestDto.getQuantity());
        product.setDiscount(requestDto.getDiscount());
        product.setTax(requestDto.getTax());
        product.setStatusId(requestDto.getProductStatus().getId());
        product.setSeller(seller);
        product.setSubCategory(subCategory);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }

    public Product toEntity(final ProductUpdateDto updateDto) {

        if(updateDto == null){
            return null;
        }

        Product product = new Product();

        User seller = new User();
        seller.setId(updateDto.getSellerId());

        product.setSeller(seller);

        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryId(updateDto.getSubCategoryId());

        product.setProductId(updateDto.getProductId());
        product.setName(updateDto.getProductName());
        product.setPrice(updateDto.getPrice());
        product.setQuantity(updateDto.getQuantity());
        product.setDiscount(updateDto.getDiscount());
        product.setTax(updateDto.getTax());
        product.setStatusId(updateDto.getProductStatus().getId());
        product.setSeller(seller);
        product.setSubCategory(subCategory);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }

    public ProductResponseDto toResponseDto(final Product product){

        if(product == null){
            return null;
        }

        ProductResponseDto dto = new ProductResponseDto();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setDiscount(product.getDiscount());
        dto.setTax(product.getTax());
        dto.setProductStatus(ProductStatus.fromId(product.getStatusId()));

        if (product.getSeller() != null) {

            dto.setSellerId(product.getSeller().getId());
        }

        if(product.getSubCategory()!=null){

            dto.setSubCategoryId(product.getSubCategory().getSubCategoryId());
        }
        return dto;
    }
}