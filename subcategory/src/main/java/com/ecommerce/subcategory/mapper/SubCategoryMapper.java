/*
         * SubCategoryMapper.java
         *
         * Version 1.1
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

// Converts between SubCategory DTOs and entities
@Component
public class SubCategoryMapper {

    //Converts a create request DTO into a SubCategory entity
    public SubCategory toEntity(
            final SubCategoryRequestDto requestDto) {

        if (requestDto == null) {
            return null;
        }

        Category category = new Category();
        category.setCategoryId(
                requestDto.getCategoryId()
        );

        SubCategory subCategory = new SubCategory();

        subCategory.setSubCategoryName(
                requestDto.getSubCategoryName()
        );

        subCategory.setCategory(category);

        return subCategory;
    }

    /**
     * Converts an update request DTO into a SubCategory entity.
     *
     * The subcategory ID is intentionally not mapped here because
     * the ID comes from the PUT path variable.
     *
     * @param updateDto update request DTO
     * @return mapped SubCategory entity
     */
    public SubCategory toEntity(
            final SubCategoryUpdateDto updateDto) {

        if (updateDto == null) {
            return null;
        }

        Category category = new Category();
        category.setCategoryId(
                updateDto.getCategoryId()
        );

        SubCategory subCategory = new SubCategory();

        subCategory.setSubCategoryName(
                updateDto.getSubCategoryName()
        );

        subCategory.setCategory(category);

        return subCategory;
    }

    /**
     * Converts a SubCategory entity into a response DTO.
     *
     * @param subCategory SubCategory entity
     * @return response DTO
     */
    public SubCategoryResponseDto toResponseDto(
            final SubCategory subCategory) {

        if (subCategory == null) {
            return null;
        }

        Integer categoryId = null;

        if (subCategory.getCategory() != null) {
            categoryId =
                    subCategory.getCategory().getCategoryId();
        }

        return new SubCategoryResponseDto(
                subCategory.getSubCategoryId(),
                subCategory.getSubCategoryName(),
                categoryId
        );
    }
}
