/*
 * CategoryController.java
 *
 * Version 1.5
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.controller;

import java.util.Collection;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.category.dto.CategoryRequestDto;
import com.ecommerce.category.dto.CategoryResponseDto;
import com.ecommerce.category.dto.CategoryUpdateDto;
import com.ecommerce.category.entity.Category;
import com.ecommerce.category.mapper.CategoryMapper;
import com.ecommerce.category.service.CategoryService;

/**
 * Handles category management operations.
 * This controller acts as an intermediary
 * between the client layer and the service layer.
 */
@RestController
@Validated
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    // Creates a CategoryController object
    public CategoryController(
            final CategoryService categoryService,
            final CategoryMapper categoryMapper) {

        this.categoryService = Objects.requireNonNull(categoryService, "CategoryService cannot be null.");
        this.categoryMapper = Objects.requireNonNull(categoryMapper, "CategoryMapper cannot be null.");
    }

    // Adds a category
    @PostMapping
    public boolean addCategory(
            @Valid
            @RequestBody
            final CategoryRequestDto requestDto) {

        Category category = categoryMapper.toEntity(requestDto);
        return categoryService.addCategory(category);
    }

    // Returns all categories
    @GetMapping
    public Collection<CategoryResponseDto> viewCategories() {

        return categoryService
                .getAllCategories()
                .stream()
                .map(categoryMapper::toResponseDto)
                .toList();
    }

    // Returns a category by id
    @GetMapping("/{categoryId}")
    public CategoryResponseDto getCategoryById(
            @PathVariable
            final Integer categoryId) {

        Category category = categoryService.getCategoryById(categoryId);
        return categoryMapper.toResponseDto(category);
    }

    // Returns a category by name
    @GetMapping("/name/{categoryName}")
    public CategoryResponseDto getCategoryByName(
            @PathVariable
            final String categoryName) {

        Category category = categoryService.getCategoryByName(categoryName);
        return categoryMapper.toResponseDto(category);
    }

    // Updates a category
    @PutMapping("/{categoryId}")
    public boolean updateCategory(
            @PathVariable
            final Integer categoryId,
            @Valid
            @RequestBody
            final CategoryUpdateDto requestDto) {

        Category category = categoryMapper.toEntity(requestDto);
        category.setCategoryId(categoryId);
        return categoryService.updateCategory(category);
    }

    // Deletes a category
    @DeleteMapping("/{categoryId}")
    public boolean deleteCategory(
            @PathVariable
            final Integer categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}