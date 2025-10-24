package com.safari.safari_2.repository;

import com.safari.safari_2.model.DriverTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DriverTourRepository extends JpaRepository<DriverTour, Long> {
    
    // Find tours by driver ID
    List<DriverTour> findByDriverId(Long driverId);
    
    // Find available tours by driver ID
    List<DriverTour> findByDriverIdAndStatus(Long driverId, String status);
    
    // Find tour by driver ID and tour ID
    Optional<DriverTour> findByDriverIdAndTourId(Long driverId, Long tourId);
    
    // Find tours by tour ID (across all drivers)
    List<DriverTour> findByTourId(Long tourId);
    
    // Find tours by status
    List<DriverTour> findByStatus(String status);
    
    // Find tours by date
    List<DriverTour> findByTourDate(LocalDate tourDate);
    
    // Find tours by driver ID and date
    List<DriverTour> findByDriverIdAndTourDate(Long driverId, LocalDate tourDate);
    
    // Find accepted tours by driver ID
    List<DriverTour> findByDriverIdAndStatusOrderByAcceptedDateDesc(Long driverId, String status);
    
    // Find tours by tourist ID
    List<DriverTour> findByTouristId(Long touristId);
    
    // Find accepted tour by tour ID (should be only one)
    Optional<DriverTour> findByTourIdAndStatus(Long tourId, String status);
    
    // Delete all tours for a specific tour ID (when tour is accepted by someone else)
    @Modifying
    @Query("DELETE FROM DriverTour dt WHERE dt.tourId = :tourId AND dt.status = 'AVAILABLE'")
    void deleteAvailableToursByTourId(@Param("tourId") Long tourId);
    
    // Update status of a specific driver tour
    @Modifying
    @Query("UPDATE DriverTour dt SET dt.status = :status, dt.acceptedDate = :acceptedDate WHERE dt.driverId = :driverId AND dt.tourId = :tourId")
    void updateDriverTourStatus(@Param("driverId") Long driverId, @Param("tourId") Long tourId, 
                               @Param("status") String status, @Param("acceptedDate") LocalDate acceptedDate);
    
    // Delete all tours for a specific tour ID (complete deletion)
    @Modifying
    @Query("DELETE FROM DriverTour dt WHERE dt.tourId = :tourId")
    void deleteByTourId(@Param("tourId") Long tourId);
}
