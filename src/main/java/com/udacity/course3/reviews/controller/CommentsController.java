package com.udacity.course3.reviews.controller;

import java.util.Optional;
import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.CommentsRepository;
import com.udacity.course3.reviews.repository.ProductsRepository;
import com.udacity.course3.reviews.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // TODO: Wire needed JPA repositories here

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private ReviewsRepository reviewsRepository;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment comment) {

        Optional<Review> optionalReview = reviewsRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            comment.setReview(optionalReview.get());
            return ResponseEntity.ok(commentsRepository.save(comment));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Review review = new Review();
        review.setId(reviewId);
        if (commentsRepository.findAllByReview(review).size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentsRepository.findAllByReview(review));
    }
}