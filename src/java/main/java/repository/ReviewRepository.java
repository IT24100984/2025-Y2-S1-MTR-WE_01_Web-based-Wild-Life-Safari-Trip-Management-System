package java.main.java.repository;

import java.main.java.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Find all reviews ordered by creation date (newest first)
    List<Review> findAllByOrderByCreatedAtDesc();

    // Find reviews by star rating
    List<Review> findByStarsOrderByCreatedAtDesc(Integer stars);

    // Find reviews with minimum star rating
    List<Review> findByStarsGreaterThanEqualOrderByCreatedAtDesc(Integer minStars);

    // Find reviews by status
    List<Review> findByStatusOrderByCreatedAtDesc(String status);
}