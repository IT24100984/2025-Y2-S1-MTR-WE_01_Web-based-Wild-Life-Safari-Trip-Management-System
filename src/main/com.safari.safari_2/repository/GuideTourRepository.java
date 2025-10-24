package com.safari.safari_2.repository;

import com.safari.safari_2.model.GuideTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuideTourRepository extends JpaRepository<GuideTour, Long> {
    
    // Find tours by guide ID
    List<GuideTour> findByGuideId(Long guideId);
    
    // Find available tours by guide ID
    List<GuideTour> findByGuideIdAndStatus(Long guideId, String status);
    
    // Find tour by guide ID and tour ID
    Optional<GuideTour> findByGuideIdAndTourId(Long guideId, Long tourId);
    
    // Find tours by tour ID (across all guides)
    List<GuideTour> findByTourId(Long tourId);
    
    // Find tours by status
    List<GuideTour> findByStatus(String status);
    
    // Find tours by date
    List<GuideTour> findByTourDate(LocalDate tourDate);
    
    // Find tours by guide ID and date
    List<GuideTour> findByGuideIdAndTourDate(Long guideId, LocalDate tourDate);
    
    // Find accepted tours by guide ID
    List<GuideTour> findByGuideIdAndStatusOrderByAcceptedDateDesc(Long guideId, String status);
    
    // Find tours by tourist ID
    List<GuideTour> findByTouristId(Long touristId);
    
    // Find accepted tour by tour ID (should be only one)
    Optional<GuideTour> findByTourIdAndStatus(Long tourId, String status);
    
    // Delete all tours for a specific tour ID (when tour is accepted by someone else)
    @Modifying
    @Query("DELETE FROM GuideTour gt WHERE gt.tourId = :tourId AND gt.status = 'AVAILABLE'")
    void deleteAvailableToursByTourId(@Param("tourId") Long tourId);
    
    // Update status of a specific guide tour
    @Modifying
    @Query("UPDATE GuideTour gt SET gt.status = :status, gt.acceptedDate = :acceptedDate WHERE gt.guideId = :guideId AND gt.tourId = :tourId")
    void updateGuideTourStatus(@Param("guideId") Long guideId, @Param("tourId") Long tourId, 
                              @Param("status") String status, @Param("acceptedDate") LocalDate acceptedDate);
    
    // Delete all tours for a specific tour ID (complete deletion)
    @Modifying
    @Query("DELETE FROM GuideTour gt WHERE gt.tourId = :tourId")
    void deleteByTourId(@Param("tourId") Long tourId);
}
