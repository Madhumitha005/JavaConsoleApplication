package com.ecommerce.review.service;

import java.util.Collection;
import java.util.Collections;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.review.entity.Review;
import com.ecommerce.review.repository.ReviewRepository;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(
            final ReviewRepository reviewRepository) {

        this.reviewRepository = reviewRepository;
    }

    // Save review
    @Caching(evict = {
            @CacheEvict(value = "reviews", allEntries = true),
            @CacheEvict(value = "reviewsByProduct", allEntries = true),
            @CacheEvict(value = "reviewsByCustomer", allEntries = true),
            @CacheEvict(value = "reviewReplies", allEntries = true),
            @CacheEvict(value = "allReviews", allEntries = true)
    })
    public boolean save(final Review review) {

        if (review == null) {
            return false;
        }
        return reviewRepository.save(review);
    }

    // Update review
    @Caching(evict = {
            @CacheEvict(value = "reviews", allEntries = true),
            @CacheEvict(value = "reviewsByProduct", allEntries = true),
            @CacheEvict(value = "reviewsByCustomer", allEntries = true),
            @CacheEvict(value = "reviewReplies", allEntries = true),
            @CacheEvict(value = "allReviews", allEntries = true)
    })
    public boolean update(final Review review) {

        if (review == null
                || review.getReviewId() <= 0) {

            return false;
        }
        return reviewRepository.update(review);
    }

    // Delete review
    @Caching(evict = {
            @CacheEvict(value = "reviews", key = "#reviewId"),
            @CacheEvict(value = "reviewsByProduct", allEntries = true),
            @CacheEvict(value = "reviewsByCustomer", allEntries = true),
            @CacheEvict(value = "reviewReplies", allEntries = true),
            @CacheEvict(value = "allReviews", allEntries = true)
    })
    public boolean delete(final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {
            return false;
        }
        return reviewRepository.delete(reviewId);
    }

    // Find review by ID
    @Cacheable(
            value = "reviews",
            key = "#reviewId")
    public Review findById(final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {
            return null;
        }
        return reviewRepository.findById(reviewId);
    }

    // Find reviews by product
    @Cacheable(
            value = "reviewsByProduct",
            key = "#productId")
    public Collection<Review> findByProductId(
            final Integer productId) {

        if (productId == null || productId <= 0) {
            return Collections.emptyList();
        }
        return reviewRepository.findByProductId(productId);
    }

    // Find reviews by customer
    @Cacheable(
            value = "reviewsByCustomer",
            key = "#customerId")
    public Collection<Review> findByCustomerId(
            final Integer customerId) {

        if (customerId == null || customerId <= 0) {
            return Collections.emptyList();
        }
        return reviewRepository.findByCustomerId(customerId);
    }

    // Find replies
    @Cacheable(
            value = "reviewReplies",
            key = "#reviewId")
    public Collection<Review> findReplies(
            final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {
            return Collections.emptyList();
        }
        return reviewRepository.findReplies(reviewId);
    }

    // Find all reviews
    @Cacheable(
            value = "allReviews",
            key = "'all'")
    public Collection<Review> findAll() {

        return reviewRepository.findAll();
    }
}