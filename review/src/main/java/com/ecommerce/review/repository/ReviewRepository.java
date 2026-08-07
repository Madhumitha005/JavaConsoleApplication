/*
 * ReviewRepository.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.review.repository;

import java.util.Collection;

import com.ecommerce.review.entity.Review;

public interface ReviewRepository {

    boolean save(Review review);

    boolean update(Review review);

    boolean delete(Integer reviewId);

    Review findById(Integer reviewId);

    Collection<Review> findByProductId(Integer productId);

    Collection<Review> findReplies(final Integer reviewId);

    Collection<Review> findByCustomerId(Integer customerId);

    Collection<Review> findAll();
}