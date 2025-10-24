package com.safari.safari_2.service;

import com.safari.safari_2.model.DriverTour;
import com.safari.safari_2.model.Tour;
import com.safari.safari_2.repository.DriverTourRepository;
import com.safari.safari_2.repository.DriverRepository;
import com.safari.safari_2.repository.UserRepository;
import com.safari.safari_2.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DriverTourService {
    
    @Autowired
    private DriverTourRepository driverTourRepository;
    
    @Autowired
    private DriverRepository driverRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TourRepository tourRepository;
    
    @Transactional
    public void createToursForAllDrivers(Long tourId, String tourName, LocalDate tourDate, 
                                        Integer numberOfPeople, String specialInstructions, 
                                        Long touristId, String touristName, String touristContact) {
        // Get all drivers
        List<com.safari.safari_2.model.Driver> allDrivers = driverRepository.findAll();
        
        // Create tour entry for each driver
        for (com.safari.safari_2.model.Driver driver : allDrivers) {
            DriverTour driverTour = new DriverTour(
                driver.getId(),
                tourId,
                tourName,
                tourDate,
                numberOfPeople,
                specialInstructions,
                touristId,
                touristName,
                touristContact
            );
            driverTourRepository.save(driverTour);
        }
    }
    
    public List<DriverTour> getToursByDriverId(Long driverId) {
        return driverTourRepository.findByDriverId(driverId);
    }
    
    public List<DriverTour> getAvailableToursByDriverId(Long driverId) {
        return driverTourRepository.findByDriverIdAndStatus(driverId, "AVAILABLE");
    }
    
    public List<DriverTour> getAcceptedToursByDriverId(Long driverId) {
        return driverTourRepository.findByDriverIdAndStatusOrderByAcceptedDateDesc(driverId, "ACCEPTED");
    }
    
    @Transactional
    public boolean acceptTour(Long driverId, Long tourId) {
        // Check if tour is still available
        Optional<DriverTour> driverTourOptional = driverTourRepository.findByDriverIdAndTourId(driverId, tourId);
        if (!driverTourOptional.isPresent() || !"AVAILABLE".equals(driverTourOptional.get().getStatus())) {
            return false; // Tour not available
        }
        
        // Check if tour is already accepted by another driver
        Optional<DriverTour> alreadyAccepted = driverTourRepository.findByTourIdAndStatus(tourId, "ACCEPTED");
        if (alreadyAccepted.isPresent()) {
            return false; // Tour already accepted by another driver
        }
        
        // Accept the tour
        driverTourRepository.updateDriverTourStatus(driverId, tourId, "ACCEPTED", LocalDate.now());
        
        // Update the main tours table with assigned driver
        Optional<Tour> tourOptional = tourRepository.findById(tourId);
        if (tourOptional.isPresent()) {
            Tour tour = tourOptional.get();
            tour.setAssignedDriverId(driverId);
            tourRepository.save(tour);
        }
        
        // Remove tour from all other drivers
        driverTourRepository.deleteAvailableToursByTourId(tourId);
        
        return true;
    }
    
    public List<DriverTour> getToursByTouristId(Long touristId) {
        return driverTourRepository.findByTouristId(touristId);
    }
    
    public Optional<DriverTour> getAcceptedTourByTourId(Long tourId) {
        return driverTourRepository.findByTourIdAndStatus(tourId, "ACCEPTED");
    }
    
    public List<DriverTour> getAllTours() {
        return driverTourRepository.findAll();
    }
    
    public List<DriverTour> getToursByStatus(String status) {
        return driverTourRepository.findByStatus(status);
    }
    
    public List<DriverTour> getToursByDate(LocalDate date) {
        return driverTourRepository.findByTourDate(date);
    }
    
    public List<DriverTour> getToursByDriverIdAndDate(Long driverId, LocalDate date) {
        return driverTourRepository.findByDriverIdAndTourDate(driverId, date);
    }
    
    @Transactional
    public void deleteToursByTourId(Long tourId) {
        driverTourRepository.deleteByTourId(tourId);
    }
}
