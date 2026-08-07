/*
 * ReviewController.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.review.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.review.entity.Review;
import com.ecommerce.review.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(final ReviewService reviewService) {

        this.reviewService = Objects.requireNonNull(reviewService, "ReviewService cannot be null.");
    }

    @PostMapping
    public boolean save(
            @RequestBody
            final Review review) {
        return reviewService.save(review);
    }

    @PutMapping
    public boolean update(
            @RequestBody
            final Review review) {
        return reviewService.update(review);
    }

    @DeleteMapping("/{reviewId}")
    public boolean delete(
            @PathVariable
            final Integer reviewId) {
        return reviewService.delete(reviewId);
    }

    @GetMapping("/{reviewId}")
    public Review findById(
            @PathVariable
            final Integer reviewId) {
        return reviewService.findById(reviewId);
    }

    @GetMapping("/product/{productId}")
    public Collection<Review> findByProductId(
            @PathVariable
            final Integer productId) {
        return reviewService.findByProductId(productId);
    }

    @GetMapping("/customer/{customerId}")
    public Collection<Review> findByCustomerId(
            @PathVariable
            final Integer customerId) {
        return reviewService.findByCustomerId(customerId);
    }

    @GetMapping
    public Collection<Review> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/reply/{reviewId}")
    public Collection<Review> findReplies(
            @PathVariable
            final Integer reviewId) {
        return reviewService.findReplies(reviewId);
    }
}