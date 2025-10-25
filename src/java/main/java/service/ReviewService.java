package java.main.java.service;

import java.main.java.model.Review;
import java.main.java.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(String name, String review, Integer stars) {
        Review newReview = new Review(name, review, stars);
        return reviewRepository.save(newReview);
    }

    public Review updateReview(Long id, String name, String review, Integer stars) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            Review existingReview = reviewOptional.get();
            existingReview.setName(name);
            existingReview.setReview(review);
            existingReview.setStars(stars);
            return reviewRepository.save(existingReview);
        }
        return null;
    }

    public boolean deleteReview(Long id) {
        try {
            reviewRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Review> getReviewsByStars(Integer stars) {
        return reviewRepository.findByStarsOrderByCreatedAtDesc(stars);
    }

    public List<Review> getReviewsByMinStars(Integer minStars) {
        return reviewRepository.findByStarsGreaterThanEqualOrderByCreatedAtDesc(minStars);
    }

    public List<Review> getApprovedReviews() {
        return reviewRepository.findByStatusOrderByCreatedAtDesc("APPROVED");
    }

    public List<Review> getPendingReviews() {
        return reviewRepository.findByStatusOrderByCreatedAtDesc("PENDING");
    }

    public List<Review> getReviewsByStatus(String status) {
        return reviewRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public Review updateReviewStatus(Long id, String status) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setStatus(status);
            return reviewRepository.save(review);
        }
        return null;
    }
}
