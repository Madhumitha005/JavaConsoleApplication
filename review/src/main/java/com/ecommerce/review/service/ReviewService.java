/*
 * ReviewService.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.review.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ecommerce.review.entity.Review;
import com.ecommerce.review.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository memoryRepository;
    private final ReviewRepository jdbcRepository;

    public ReviewService(

            @Qualifier("inMemoryReviewRepository")
            final ReviewRepository memoryRepository,
            @Qualifier("jdbcReviewRepository")
            final ReviewRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    public boolean save(final Review review) {

        if (review == null) {
            return false;
        }

        boolean jdbcSaved = jdbcRepository.save(review);
        if (jdbcSaved) {
            memoryRepository.save(review);
        }
        return jdbcSaved;
    }

    public boolean update(final Review review) {

        if (review == null) {
            return false;
        }

        boolean jdbcUpdated = jdbcRepository.update(review);

        if (jdbcUpdated) {
            memoryRepository.update(review);
        }
        return jdbcUpdated;
    }

    public boolean delete(final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {

            return false;
        }

        boolean jdbcDeleted = jdbcRepository.delete(reviewId);

        if (jdbcDeleted) {
            memoryRepository.delete(reviewId);
        }
        return jdbcDeleted;
    }

    public Review findById(final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {

            return null;
        }

        Review review = memoryRepository.findById(reviewId);

        if (review == null) {

            review = jdbcRepository.findById(reviewId);
        }
        return review;
    }

    public Collection<Review> findByProductId(final Integer productId) {

        if (productId == null || productId <= 0) {

            return Collections.emptyList();
        }

        return jdbcRepository.findByProductId(productId);
    }

    public Collection<Review> findByCustomerId(final Integer customerId) {

        if (customerId == null || customerId <= 0) {

            return Collections.emptyList();
        }

        return jdbcRepository.findByCustomerId(customerId);
    }

    public Collection<Review> findReplies(
            final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {

            return Collections.emptyList();
        }

        return jdbcRepository.findReplies(reviewId);
    }

    public Collection<Review> findAll() {

        return jdbcRepository.findAll();
    }
}