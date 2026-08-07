/*
 * ReviewMapper.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.review.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.review.dto.ReviewRequestDto;
import com.ecommerce.review.dto.ReviewResponseDto;
import com.ecommerce.review.dto.ReviewUpdateDto;
import com.ecommerce.review.entity.Review;

@Component
public class ReviewMapper {

    public Review toEntity(final ReviewRequestDto reviewRequestDto) {

        if (reviewRequestDto == null) {
            return null;
        }

        Review review = new Review();
        review.setUserId(reviewRequestDto.getUserId());
        review.setProductId(reviewRequestDto.getProductId());
        review.setRating(reviewRequestDto.getRating());
        review.setComment(reviewRequestDto.getComment());
        review.setReplyToReviewId(reviewRequestDto.getReplyToReviewId());

        return review;
    }

    public Review toEntity(
            final ReviewUpdateDto reviewUpdateDto) {

        if (reviewUpdateDto == null) {
            return null;
        }

        Review review = new Review();
        review.setReviewId(reviewUpdateDto.getReviewId());
        review.setRating(reviewUpdateDto.getRating());
        review.setComment(reviewUpdateDto.getComment());
        review.setReplyToReviewId(reviewUpdateDto.getReplyToReviewId());

        return review;
    }

    public ReviewResponseDto toResponseDto(final Review review) {

        if (review == null) {
            return null;
        }

        ReviewResponseDto responseDto = new ReviewResponseDto();
        responseDto.setReviewId(review.getReviewId());
        responseDto.setUserId(review.getUserId());
        responseDto.setProductId(review.getProductId());
        responseDto.setRating(review.getRating());
        responseDto.setComment(review.getComment());
        responseDto.setReplyToReviewId(review.getReplyToReviewId());

        return responseDto;
    }
}