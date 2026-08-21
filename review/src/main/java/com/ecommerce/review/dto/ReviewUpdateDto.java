package com.ecommerce.review.dto;

import com.ecommerce.common.validation.UpdateGroup;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReviewUpdateDto {

    @NotNull(
            message = "Review ID is required.",
            groups = UpdateGroup.class
    )
    @Positive(
            message = "Invalid review ID.",
            groups = UpdateGroup.class
    )
    private Integer reviewId;

    @NotNull(
            message = "Rating is required.",
            groups = UpdateGroup.class
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

    @Positive(
            message = "Invalid reply review ID.",
            groups = UpdateGroup.class
    )
    private Integer replyToReviewId;

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(final Integer reviewId) {
        this.reviewId = reviewId;
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

    public void setReplyToReviewId(
            final Integer replyToReviewId) {

        this.replyToReviewId = replyToReviewId;
    }
}