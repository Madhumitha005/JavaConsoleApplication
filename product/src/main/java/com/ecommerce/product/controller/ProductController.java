/*
 * ProductController.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.product.controller;

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

import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.dto.ProductUpdateDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.service.ProductService;

// Handles product REST API operations
@RestController
@Validated
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(
            final ProductService productService,
            final ProductMapper productMapper) {

        this.productService = Objects.requireNonNull(productService, "ProductService cannot be null.");
        this.productMapper = Objects.requireNonNull(productMapper, "ProductMapper cannot be null.");
    }

    // Create Product
    @PostMapping
    public ResponseEntity<Boolean> addProduct(
            @Valid
            @RequestBody
            final ProductRequestDto requestDto) {

        Product product = productMapper.toEntity(requestDto);
        boolean saved = productService.save(product);

        return ResponseEntity.ok(saved);
    }

    // Get All Products
    @GetMapping
    public ResponseEntity<Collection<ProductResponseDto>> getAllProducts() {

        Collection<ProductResponseDto> products = productService.findAll()
                        .stream()
                        .map(productMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(products);
    }

    // Get product by id
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable
            final Integer productId) {

        Product product = productService.findById(productId);

        if(product == null) {

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toResponseDto(product));
    }

    // Get product by name
    @GetMapping("/name/{productName}")
    public ResponseEntity<ProductResponseDto> getProductByName(
            @PathVariable
            final String productName) {

        Product product = productService.findByName(productName);

        if(product == null) {

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toResponseDto(product));
    }

    // Update Product
    @PutMapping("/{productId}")
    public ResponseEntity<Boolean> updateProduct(
            @PathVariable
            final Integer productId,
            @Valid
            @RequestBody
            final ProductUpdateDto updateDto) {


        Product product = productMapper.toEntity(updateDto);
        product.setProductId(productId);
        boolean updated = productService.update(product);

        return ResponseEntity.ok(updated);
    }

    // Delete Product
    @DeleteMapping("/{productId}")
    public ResponseEntity<Boolean> deleteProduct(
            @PathVariable
            final Integer productId) {

        boolean deleted = productService.delete(productId);

        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Collection<ProductResponseDto>> getProductsBySellerId(
            @PathVariable
            final Integer sellerId) {

        Collection<ProductResponseDto> products = productService.findBySellerId(sellerId)
                        .stream()
                        .map(productMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(products);
    }
}