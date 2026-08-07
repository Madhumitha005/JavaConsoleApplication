/*
 * JdbcReviewRepository.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.review.repository.jdbc;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.review.entity.Review;
import com.ecommerce.review.repository.ReviewRepository;

@Repository("jdbcReviewRepository")
public class JdbcReviewRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JdbcTemplate cannot be null.");
    }

    private static final RowMapper<Review> REVIEW_ROW_MAPPER = (resultSet, rowNum) -> {

        Review review = new Review();
        review.setReviewId(resultSet.getInt("review_id"));
        review.setUserId(resultSet.getInt("user_id"));
        review.setProductId(resultSet.getInt("product_id"));
        review.setRating(resultSet.getInt("rating"));
        review.setComment(resultSet.getString("comment"));
        review.setReplyToReviewId(resultSet.getInt("reply_to_review_id"));

        return review;
    };

    @Override
    public boolean save(final Review review) {

        final String sql = """
                INSERT INTO review
                (
                    user_id,
                    product_id,
                    rating,
                    comment,
                    reply_to_review_id
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        int rowsAffected =
                jdbcTemplate.update(
                        sql,
                        review.getUserId(),
                        review.getProductId(),
                        review.getRating(),
                        review.getComment(),
                        review.getReplyToReviewId()
                );

        return rowsAffected > 0;
    }

    @Override
    public boolean update(final Review review) {

        final String sql = """
                UPDATE review
                SET
                    rating = ?,
                    comment = ?,
                    reply_to_review_id = ?
                WHERE review_id = ?
                """;

        int rowsAffected = jdbcTemplate.update(
                        sql,
                        review.getRating(),
                        review.getComment(),
                        review.getReplyToReviewId(),
                        review.getReviewId()
                );

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(final Integer reviewId) {

        final String sql = """
                DELETE FROM review
                WHERE review_id = ?
                """;

        int rowsAffected = jdbcTemplate.update(
                        sql,
                        reviewId);

        return rowsAffected > 0;
    }

    @Override
    public Review findById(final Integer reviewId) {

        final String sql = """
                SELECT *
                FROM review
                WHERE review_id = ?
                """;

        List<Review> reviews = jdbcTemplate.query(
                        sql,
                        REVIEW_ROW_MAPPER,
                        reviewId
                );

        return reviews.isEmpty()
                ? null
                : reviews.getFirst();
    }

    @Override
    public Collection<Review> findByProductId(final Integer productId) {

        final String sql = """
                SELECT *
                FROM review
                WHERE product_id = ?
                """;

        return jdbcTemplate.query(
                sql,
                REVIEW_ROW_MAPPER,
                productId
        );
    }

    @Override
    public Collection<Review> findByCustomerId(final Integer customerId) {

        final String sql = """
                SELECT *
                FROM review
                WHERE user_id = ?
                """;

        return jdbcTemplate.query(
                sql,
                REVIEW_ROW_MAPPER,
                customerId
        );
    }

    @Override
    public Collection<Review> findAll() {

        final String sql = """
                SELECT *
                FROM review
                ORDER BY review_id
                """;

        return jdbcTemplate.query(
                sql,
                REVIEW_ROW_MAPPER
        );
    }

    public Collection<Review> findReplies(
            final Integer reviewId) {

        final String sql = """
            SELECT *
            FROM review
            WHERE reply_to_review_id = ?
            """;

        return jdbcTemplate.query(
                sql,
                REVIEW_ROW_MAPPER,
                reviewId
        );
    }
}