/*
 * ReviewRequestDto.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.review.dto;

import com.ecommerce.common.validation.CreateGroup;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReviewRequestDto {

    @NotNull(
            message = "Product ID is required.",
            groups = CreateGroup.class
    )
    @Positive(
            message = "Invalid product ID.",
            groups = CreateGroup.class
    )
    private Integer productId;

    @NotNull(
            message = "Rating is required.",
            groups = CreateGroup.class
    )
    @Min(
            value = 1,
            message = "Rating must be at least 1."
    )
    @Max(
            value = 5,
            message = "Rating cannot exceed 5."
    )
    private Integer rating;

    @NotBlank(
            message = "Comment cannot be empty."
    )
    private String comment;

    @NotNull(
            message = "User ID is required.",
            groups = CreateGroup.class
    )
    @Positive(
            message = "Invalid user ID.",
            groups = CreateGroup.class
    )
    private Integer userId;

    @Positive(
            message = "Invalid reply review ID.",
            groups = CreateGroup.class
    )
    private Integer replyToReviewId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public Integer getReplyToReviewId() {
        return replyToReviewId;
    }

    public void setReplyToReviewId(
            final Integer replyToReviewId) {
        this.replyToReviewId = replyToReviewId;
    }
}