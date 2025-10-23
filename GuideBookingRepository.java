package com.safari.safari_2.repository;

import com.safari.safari_2.model.GuideBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuideBookingRepository extends JpaRepository<GuideBooking, Long> {
    
    // Find bookings by guide ID
    List<GuideBooking> findByGuideId(Long guideId);
    
    // Find booking by booking ID
    Optional<GuideBooking> findByBookingId(String bookingId);
    
    // Find bookings by status
    List<GuideBooking> findByStatus(String status);
    
    // Find bookings by guide ID and status
    List<GuideBooking> findByGuideIdAndStatus(Long guideId, String status);
    
    // Find bookings by date
    List<GuideBooking> findByDate(LocalDate date);
    
    // Find bookings by guide ID and date
    List<GuideBooking> findByGuideIdAndDate(Long guideId, LocalDate date);
    
    // Find bookings by tour name
    List<GuideBooking> findByTourName(String tourName);
    
    // Find bookings by guide ID and tour name
    List<GuideBooking> findByGuideIdAndTourName(Long guideId, String tourName);
}






