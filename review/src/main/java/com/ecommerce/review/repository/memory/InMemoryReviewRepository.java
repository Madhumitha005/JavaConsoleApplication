/*
 * InMemoryReviewRepository.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.review.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.review.entity.Review;
import com.ecommerce.review.repository.ReviewRepository;

@Repository("inMemoryReviewRepository")
public class InMemoryReviewRepository
        implements ReviewRepository {

    private final List<Review> reviews;

    public InMemoryReviewRepository() {

        this.reviews = new ArrayList<>();
    }

    @Override
    public boolean save(final Review review) {

        if (review == null) {
            return false;
        }

        review.setReviewId(IdGenerator.getInstance().nextReviewId());
        return reviews.add(review);
    }

    @Override
    public boolean update(final Review review) {

        if (review == null) {
            return false;
        }

        for (int index = 0;
             index < reviews.size();
             index++) {

            if (reviews.get(index)
                    .getReviewId()
                    .equals(review.getReviewId())) {

                reviews.set(index, review);

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {

            return false;
        }

        return reviews.removeIf(review -> review.getReviewId().equals(reviewId));
    }

    @Override
    public Review findById(final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {

            return null;
        }

        for (Review review : reviews) {

            if (review.getReviewId().equals(reviewId)) {

                return review;
            }
        }
        return null;
    }

    @Override
    public Collection<Review> findByProductId(final Integer productId) {

        Collection<Review> result = new ArrayList<>();

        for (Review review : reviews) {

            if (review.getProductId().equals(productId)) {

                result.add(review);
            }
        }
        return result;
    }

    @Override
    public Collection<Review> findByCustomerId(final Integer customerId) {

        Collection<Review> result = new ArrayList<>();

        for (Review review : reviews) {

            if (review.getUserId().equals(customerId)) {

                result.add(review);
            }
        }
        return result;
    }

    public Collection<Review> findReplies(final Integer reviewId) {

        Collection<Review> result = new ArrayList<>();

        for (Review review : reviews) {

            if (review.getReplyToReviewId() != null
                    && review.getReplyToReviewId().equals(reviewId)) {

                result.add(review);
            }
        }
        return result;
    }

    @Override
    public Collection<Review> findAll() {

        return new ArrayList<>(reviews);
    }
}