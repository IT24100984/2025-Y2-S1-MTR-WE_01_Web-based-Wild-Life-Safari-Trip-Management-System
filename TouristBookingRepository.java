package com.safari.safari_2.repository;

import com.safari.safari_2.model.TouristBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TouristBookingRepository extends JpaRepository<TouristBooking, Long> {
    
    // Find bookings by tourist ID
    List<TouristBooking> findByTouristId(Long touristId);
    
    // Find booking by booking ID
    Optional<TouristBooking> findByBookingId(String bookingId);
    
    // Find bookings by status
    List<TouristBooking> findByStatus(String status);
    
    // Find bookings by tourist ID and status
    List<TouristBooking> findByTouristIdAndStatus(Long touristId, String status);
    
    // Find bookings by date
    List<TouristBooking> findByDate(LocalDate date);
    
    // Find bookings by tourist ID and date
    List<TouristBooking> findByTouristIdAndDate(Long touristId, LocalDate date);
    
    // Find bookings by tour name
    List<TouristBooking> findByTourName(String tourName);
    
    // Find bookings by tourist ID and tour name
    List<TouristBooking> findByTouristIdAndTourName(Long touristId, String tourName);
}






