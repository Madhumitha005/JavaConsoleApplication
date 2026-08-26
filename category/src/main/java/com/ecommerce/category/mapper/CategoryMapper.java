/*
 * CategoryMapper.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.mapper;

import org.springframework.stereotype.Component;
import com.ecommerce.category.dto.CategoryRequestDto;
import com.ecommerce.category.dto.CategoryResponseDto;
import com.ecommerce.category.dto.CategoryUpdateDto;
import com.ecommerce.category.entity.Category;

/**
 * Converts Category entities into DTO objects
 * and DTO objects into Category entities
 */
@Component
public class CategoryMapper {

    // Converts CategoryRequestDto into Category
    public Category toEntity(final CategoryRequestDto requestDto) {

        if (requestDto == null) {
            return null;
        }
        Category category = new Category();
        category.setCategoryName(requestDto.getCategoryName());
        return category;
    }

    // Converts CategoryUpdateDto into Category
    public Category toEntity(final CategoryUpdateDto updateDto) {

        if (updateDto == null) {
            return null;
        }
        Category category = new Category();
        category.setCategoryName(updateDto.getCategoryName());
        return category;
    }

    // Converts Category into CategoryResponseDto
    public CategoryResponseDto toResponseDto(final Category category) {

        if (category == null) {
            return null;
        }
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setCategoryId(category.getCategoryId());
        responseDto.setCategoryName(category.getCategoryName());
        return responseDto;
    }
}