package com.safari.reviewmanagement.controller;

import com.safari.reviewmanagement.model.Review;
import com.safari.reviewmanagement.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getApprovedReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        if (review.isPresent()) {
            return ResponseEntity.ok(review.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createReview(@RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            String review = (String) request.get("review");
            Integer stars = (Integer) request.get("stars");

            // Validation
            if (name == null || name.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Name is required");
                return ResponseEntity.badRequest().body(response);
            }

            if (review == null || review.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Review text is required");
                return ResponseEntity.badRequest().body(response);
            }

            if (stars == null || stars < 1 || stars > 5) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Stars must be between 1 and 5");
                return ResponseEntity.badRequest().body(response);
            }

            Review newReview = reviewService.createReview(name, review, stars);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Review created successfully");
            response.put("review", newReview);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to create review: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateReview(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            String review = (String) request.get("review");
            Integer stars = (Integer) request.get("stars");

            Review updatedReview = reviewService.updateReview(id, name, review, stars);

            Map<String, Object> response = new HashMap<>();
            if (updatedReview != null) {
                response.put("success", true);
                response.put("message", "Review updated successfully");
                response.put("review", updatedReview);
            } else {
                response.put("success", false);
                response.put("message", "Review not found");
            }
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update review: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable Long id) {
        try {
            boolean success = reviewService.deleteReview(id);

            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "Review deleted successfully");
            } else {
                response.put("success", false);
                response.put("message", "Review not found or could not be deleted");
            }
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to delete review: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/stars/{stars}")
    public ResponseEntity<List<Review>> getReviewsByStars(@PathVariable Integer stars) {
        List<Review> reviews = reviewService.getReviewsByStars(stars);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/min-stars/{minStars}")
    public ResponseEntity<List<Review>> getReviewsByMinStars(@PathVariable Integer minStars) {
        List<Review> reviews = reviewService.getReviewsByMinStars(minStars);
        return ResponseEntity.ok(reviews);
    }

    // Admin endpoints for review management
    @GetMapping("/admin/pending")
    public ResponseEntity<List<Review>> getPendingReviews() {
        List<Review> reviews = reviewService.getPendingReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<Review>> getAllReviewsForAdmin() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateReviewStatus(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            String status = (String) request.get("status");

            if (status == null || (!status.equals("APPROVED") && !status.equals("REJECTED"))) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Status must be APPROVED or REJECTED");
                return ResponseEntity.badRequest().body(response);
            }

            Review updatedReview = reviewService.updateReviewStatus(id, status);

            Map<String, Object> response = new HashMap<>();
            if (updatedReview != null) {
                response.put("success", true);
                response.put("message", "Review status updated successfully");
                response.put("review", updatedReview);
            } else {
                response.put("success", false);
                response.put("message", "Review not found");
            }
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update review status: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}