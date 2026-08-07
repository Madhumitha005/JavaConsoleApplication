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

public class ReviewRequestDto {

    private Integer productId;
    private Integer rating;
    private String comment;
    private Integer userId;
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