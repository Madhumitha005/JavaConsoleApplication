/*
 * SubCategoryController.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.subcategory.controller;

import java.util.Collection;
import java.util.Objects;

import jakarta.validation.Valid;

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

import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.subcategory.dto.SubCategoryRequestDto;
import com.ecommerce.subcategory.dto.SubCategoryResponseDto;
import com.ecommerce.subcategory.dto.SubCategoryUpdateDto;
import com.ecommerce.subcategory.entity.SubCategory;
import com.ecommerce.subcategory.mapper.SubCategoryMapper;
import com.ecommerce.subcategory.service.SubCategoryService;

// Handles subcategory operations.
@RestController
@Validated
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;
    private final SubCategoryMapper subCategoryMapper;

    // Creates a SubCategoryController object
    public SubCategoryController(
            final SubCategoryService subCategoryService,
            final SubCategoryMapper subCategoryMapper) {

        this.subCategoryService = Objects.requireNonNull(subCategoryService, "SubCategoryService cannot be null.");
        this.subCategoryMapper = Objects.requireNonNull(subCategoryMapper, "SubCategoryMapper cannot be null.");
    }

    // Adds a new subcategory
    @PostMapping
    public ResponseEntity<ApiResponse> addSubCategory(
            @Valid
            @RequestBody
            final SubCategoryRequestDto requestDto) {

        SubCategory subCategory = subCategoryMapper.toEntity(requestDto);
        subCategoryService.addSubCategory(subCategory);

        return ResponseEntity.ok(new ApiResponse(true, "Subcategory added successfully.",null));
    }

    // Retrieves a subcategory using its Id
    @GetMapping("/{subCategoryId}")
    public ResponseEntity<SubCategoryResponseDto> getSubCategoryById(
            @PathVariable
            final Integer subCategoryId) {

        SubCategory subCategory = subCategoryService.getSubCategoryById(subCategoryId);

        return ResponseEntity.ok(subCategoryMapper.toResponseDto(subCategory));
    }

    // Retrieves all subcategories
    @GetMapping
    public ResponseEntity<Collection<SubCategoryResponseDto>> viewSubCategories() {

        Collection<SubCategoryResponseDto> responseDtos = subCategoryService
                        .getAllSubCategories()
                        .stream()
                        .map(subCategoryMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(responseDtos);
    }

    // Retrieves subcategories by category Id
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Collection<SubCategoryResponseDto>> viewSubCategoriesByCategoryId(
            @PathVariable
            final Integer categoryId) {

        Collection<SubCategoryResponseDto> responseDtos = subCategoryService
                        .getSubCategoriesByCategoryId(categoryId)
                        .stream()
                        .map(subCategoryMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(responseDtos);
    }

    // Updates an existing subcategory
    @PutMapping("/{subCategoryId}")
    public ResponseEntity<ApiResponse> updateSubCategory(
            @PathVariable
            final Integer subCategoryId,
            @Valid
            @RequestBody
            final SubCategoryUpdateDto updateDto) {

        SubCategory subCategory = subCategoryMapper.toEntity(updateDto);
        subCategory.setSubCategoryId(subCategoryId);
        subCategoryService.updateSubCategory(subCategory);

        return ResponseEntity.ok(new ApiResponse(true, "Subcategory updated successfully.",null));
    }

    // Deletes a subcategory
    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<ApiResponse> deleteSubCategory(
            @PathVariable
            final Integer subCategoryId) {

        subCategoryService.deleteSubCategory(subCategoryId);

        return ResponseEntity.ok(new ApiResponse(true, "Subcategory deleted successfully.",null));
    }

}