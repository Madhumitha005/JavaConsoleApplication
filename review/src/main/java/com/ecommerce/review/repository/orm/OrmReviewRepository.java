package com.ecommerce.review.repository.orm;

import java.util.Collection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.review.entity.Review;
import com.ecommerce.review.repository.ReviewRepository;

@Repository
@Transactional
public class OrmReviewRepository implements ReviewRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(final Review review) {

        if (review == null) {
            return false;
        }
        entityManager.persist(review);
        return true;
    }

    @Override
    public boolean update(final Review review) {

        if (review == null || review.getReviewId() == null
                || review.getReviewId() <= 0) {

            return false;
        }
        Review existing = entityManager.find(Review.class, review.getReviewId());

        if (existing == null) {
            return false;
        }
        existing.setRating(review.getRating());
        existing.setComment(review.getComment());
        existing.setReplyToReviewId(review.getReplyToReviewId());
        return true;
    }

    @Override
    public boolean delete(final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {
            return false;
        }
        Review review = entityManager.find(Review.class, reviewId);

        if (review == null) {
            return false;
        }
        entityManager.remove(review);
        return true;
    }

    @Override
    public Review findById(final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {
            return null;
        }
        return entityManager.find(Review.class, reviewId);
    }

    @Override
    public Collection<Review> findByProductId(
            final Integer productId) {

        if (productId == null || productId <= 0) {
            return java.util.Collections.emptyList();
        }

        return entityManager.createQuery(
                        "SELECT e FROM Review e " +
                                "WHERE e.productId = :productId",
                        Review.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public Collection<Review> findReplies(
            final Integer reviewId) {

        if (reviewId == null || reviewId <= 0) {
            return java.util.Collections.emptyList();
        }

        return entityManager.createQuery(
                        "SELECT e FROM Review e " +
                                "WHERE e.replyToReviewId = :reviewId",
                        Review.class)
                .setParameter("reviewId", reviewId)
                .getResultList();
    }

    @Override
    public Collection<Review> findByCustomerId(
            final Integer customerId) {

        if (customerId == null || customerId <= 0) {
            return java.util.Collections.emptyList();
        }

        return entityManager.createQuery(
                        "SELECT e FROM Review e " +
                                "WHERE e.userId = :customerId",
                        Review.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    @Override
    public Collection<Review> findAll() {

        return entityManager.createQuery(
                        "SELECT e FROM Review e",
                        Review.class)
                .getResultList();
    }
}