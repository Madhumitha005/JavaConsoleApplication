
package com.ecommerce.review.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    // Saves a review and clears review-related caches
    @CacheEvict(
            cacheNames = {
                    "reviews",
                    "reviewsByProduct",
                    "reviewsByCustomer",
                    "reviewReplies",
                    "allReviews"
            },
            allEntries = true,
            condition = "#result == true"
    )
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

    // Updates a review and clears old cached data
    @CacheEvict(
            cacheNames = {
                    "reviews",
                    "reviewsByProduct",
                    "reviewsByCustomer",
                    "reviewReplies",
                    "allReviews"
            },
            allEntries = true,
            condition = "#result == true"
    )
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

    // Deletes a review and clears related caches
    @CacheEvict(
            cacheNames = {
                    "reviews",
                    "reviewsByProduct",
                    "reviewsByCustomer",
                    "reviewReplies",
                    "allReviews"
            },
            allEntries = true,
            condition = "#result == true"
    )
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

    // Finds a review by ID using Redis cache
    @Cacheable(
            value = "reviews",
            key = "#reviewId"
    )
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

    // Finds reviews by product ID using Redis cache
    @Cacheable(
            value = "reviewsByProduct",
            key = "#productId"
    )
    public Collection<Review> findByProductId(
            final Integer productId) {

        if (productId == null || productId <= 0) {

            return Collections.emptyList();
        }

        return jdbcRepository.findByProductId(productId);
    }

    // Finds reviews by customer ID using Redis cache
    @Cacheable(
            value = "reviewsByCustomer",
            key = "#customerId"
    )
    public Collection<Review> findByCustomerId(
            final Integer customerId) {

        if (customerId == null || customerId <= 0) {

            return Collections.emptyList();
        }

        return jdbcRepository.findByCustomerId(customerId);
    }

    // Finds replies for a review using Redis cache
    @Cacheable(
            value = "reviewReplies",
            key = "#reviewId"
    )
    public Collection<Review> findReplies(
            final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {

            return Collections.emptyList();
        }

        return jdbcRepository.findReplies(reviewId);
    }

    // Finds all reviews using Redis cache
    @Cacheable(
            value = "allReviews"
    )
    public Collection<Review> findAll() {

        return jdbcRepository.findAll();
    }
}