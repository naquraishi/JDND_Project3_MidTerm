package com.udacity.course3.reviews.controller;

import java.util.List;
import java.util.Optional;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.ProductsRepository;
import com.udacity.course3.reviews.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    // TODO: Wire JPA repositories here

    @Autowired
    ReviewsRepository reviewsRepository;

    @Autowired
    ProductsRepository productsRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId,
                                                    @RequestBody Review review) {
        Optional<Product> productOptional = productsRepository.findById(productId);
        if (productOptional.isPresent()) {
            review.setProduct(productOptional.get());
            return ResponseEntity.ok(reviewsRepository.save(review));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<?> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Product product = new Product();
        product.setId(productId);
        if (reviewsRepository.findAllByProduct(product).size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviewsRepository.findAllByProduct(product));
    }
}