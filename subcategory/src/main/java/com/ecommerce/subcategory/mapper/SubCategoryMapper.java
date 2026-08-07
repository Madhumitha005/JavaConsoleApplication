/*
 * SubCategoryMapper.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.subcategory.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.category.entity.Category;
import com.ecommerce.subcategory.dto.SubCategoryRequestDto;
import com.ecommerce.subcategory.dto.SubCategoryResponseDto;
import com.ecommerce.subcategory.dto.SubCategoryUpdateDto;
import com.ecommerce.subcategory.entity.SubCategory;

@Component
public class SubCategoryMapper {

    // Converts request DTO to entity
    public SubCategory toEntity(final SubCategoryRequestDto requestDto) {

        if (requestDto == null) {

            return null;
        }

        Category category = new Category();
        category.setCategoryId(requestDto.getCategoryId());

        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(requestDto.getSubCategoryName());
        subCategory.setCategory(category);

        return subCategory;
    }

    // Converts update DTO to entity
    public SubCategory toEntity(final SubCategoryUpdateDto updateDto) {

        if (updateDto == null) {

            return null;
        }

        Category category = new Category();
        category.setCategoryId(updateDto.getCategoryId());

        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryId(updateDto.getSubCategoryId());
        subCategory.setSubCategoryName(updateDto.getSubCategoryName());
        subCategory.setCategory(category);

        return subCategory;
    }

    // Converts entity to response DTO
    public SubCategoryResponseDto toResponseDto(final SubCategory subCategory) {

        if (subCategory == null) {

            return null;
        }

        SubCategoryResponseDto responseDto = new SubCategoryResponseDto();

        responseDto.setSubCategoryId(subCategory.getSubCategoryId());
        responseDto.setSubCategoryName(subCategory.getSubCategoryName());

        if (subCategory.getCategory() != null) {

            responseDto.setCategoryId(subCategory.getCategory().getCategoryId());
        }

        return responseDto;
    }
}