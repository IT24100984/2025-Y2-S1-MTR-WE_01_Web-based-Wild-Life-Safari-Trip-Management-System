package com.safari.safari_2.repository;

import com.safari.safari_2.model.DriverBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DriverBookingRepository extends JpaRepository<DriverBooking, Long> {
    
    // Find bookings by driver ID
    List<DriverBooking> findByDriverId(Long driverId);
    
    // Find booking by booking ID
    Optional<DriverBooking> findByBookingId(String bookingId);
    
    // Find bookings by status
    List<DriverBooking> findByStatus(String status);
    
    // Find bookings by driver ID and status
    List<DriverBooking> findByDriverIdAndStatus(Long driverId, String status);
    
    // Find bookings by date
    List<DriverBooking> findByDate(LocalDate date);
    
    // Find bookings by driver ID and date
    List<DriverBooking> findByDriverIdAndDate(Long driverId, LocalDate date);
    
    // Find bookings by tour name
    List<DriverBooking> findByTourName(String tourName);
    
    // Find bookings by driver ID and tour name
    List<DriverBooking> findByDriverIdAndTourName(Long driverId, String tourName);
}






