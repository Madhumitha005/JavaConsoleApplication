/*
 * Review.java
 *
 * Version 1.1
 *
 * August 21, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.review.entity;
import java.io.Serializable;
import com.ecommerce.common.validation.CreateGroup;
import com.ecommerce.common.validation.UpdateGroup;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "review")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    @Positive(
            message = "Invalid review id.",
            groups = UpdateGroup.class
    )
    private Integer reviewId;

    @Column(name = "user_id", nullable = false)
    @Positive(
            message = "Invalid User id.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private Integer userId;

    @Column(name = "product_id", nullable = false)
    @Positive(
            message = "Invalid product id.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private Integer productId;

    @Column(name = "rating", nullable = false)
    @Min(
            value = 1,
            message = "Rating must be at least 1."
    )
    @Max(
            value = 5,
            message = "Rating cannot exceed 5."
    )
    private Integer rating;

    @Column(name = "comment", nullable = false)
    @NotBlank(
            message = "Comment cannot be empty."
    )
    private String comment;

    @Column(name = "reply_to_review_id")
    @Positive(
            message = "Invalid reply review id.",
            groups = {
                    CreateGroup.class,
                    UpdateGroup.class
            }
    )
    private Integer replyToReviewId;

    public Review() {
    }

    public Review(
            final Integer reviewId,
            final Integer userId,
            final Integer productId,
            final Integer rating,
            final String comment,
            final Integer replyToReviewId) {

        this.reviewId = reviewId;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.replyToReviewId = replyToReviewId;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(final Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(final Integer productId) {
        this.productId = productId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(final Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public Integer getReplyToReviewId() {
        return replyToReviewId;
    }

    public void setReplyToReviewId(final Integer replyToReviewId) {
        this.replyToReviewId = replyToReviewId;
    }

    @Override
    public String toString() {
        return "Review{"
                + "reviewId=" + reviewId
                + ", userId=" + userId
                + ", productId=" + productId
                + ", rating=" + rating
                + ", comment='" + comment + '\''
                + ", replyToReviewId=" + replyToReviewId
                + '}';
    }
}