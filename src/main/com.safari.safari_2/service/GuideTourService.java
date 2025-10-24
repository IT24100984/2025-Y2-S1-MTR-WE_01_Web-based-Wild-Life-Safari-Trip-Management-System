package com.safari.safari_2.service;

import com.safari.safari_2.model.GuideTour;
import com.safari.safari_2.model.Tour;
import com.safari.safari_2.repository.GuideTourRepository;
import com.safari.safari_2.repository.GuideRepository;
import com.safari.safari_2.repository.UserRepository;
import com.safari.safari_2.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GuideTourService {
    
    @Autowired
    private GuideTourRepository guideTourRepository;
    
    @Autowired
    private GuideRepository guideRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TourRepository tourRepository;
    
    @Transactional
    public void createToursForAllGuides(Long tourId, String tourName, LocalDate tourDate, 
                                        Integer numberOfPeople, String specialInstructions, 
                                        Long touristId, String touristName, String touristContact) {
        // Get all guides
        List<com.safari.safari_2.model.Guide> allGuides = guideRepository.findAll();
        
        // Create tour entry for each guide
        for (com.safari.safari_2.model.Guide guide : allGuides) {
            GuideTour guideTour = new GuideTour(
                guide.getId(),
                tourId,
                tourName,
                tourDate,
                numberOfPeople,
                specialInstructions,
                touristId,
                touristName,
                touristContact
            );
            guideTourRepository.save(guideTour);
        }
    }
    
    public List<GuideTour> getToursByGuideId(Long guideId) {
        return guideTourRepository.findByGuideId(guideId);
    }
    
    public List<GuideTour> getAvailableToursByGuideId(Long guideId) {
        return guideTourRepository.findByGuideIdAndStatus(guideId, "AVAILABLE");
    }
    
    public List<GuideTour> getAcceptedToursByGuideId(Long guideId) {
        return guideTourRepository.findByGuideIdAndStatusOrderByAcceptedDateDesc(guideId, "ACCEPTED");
    }
    
    @Transactional
    public boolean acceptTour(Long guideId, Long tourId) {
        // Check if tour is still available
        Optional<GuideTour> guideTourOptional = guideTourRepository.findByGuideIdAndTourId(guideId, tourId);
        if (!guideTourOptional.isPresent() || !"AVAILABLE".equals(guideTourOptional.get().getStatus())) {
            return false; // Tour not available
        }
        
        // Check if tour is already accepted by another guide
        Optional<GuideTour> alreadyAccepted = guideTourRepository.findByTourIdAndStatus(tourId, "ACCEPTED");
        if (alreadyAccepted.isPresent()) {
            return false; // Tour already accepted by another guide
        }
        
        // Accept the tour
        guideTourRepository.updateGuideTourStatus(guideId, tourId, "ACCEPTED", LocalDate.now());
        
        // Update the main tours table with assigned guide
        Optional<Tour> tourOptional = tourRepository.findById(tourId);
        if (tourOptional.isPresent()) {
            Tour tour = tourOptional.get();
            tour.setAssignedGuideId(guideId);
            tourRepository.save(tour);
        }
        
        // Remove tour from all other guides
        guideTourRepository.deleteAvailableToursByTourId(tourId);
        
        return true;
    }
    
    public List<GuideTour> getToursByTouristId(Long touristId) {
        return guideTourRepository.findByTouristId(touristId);
    }
    
    public Optional<GuideTour> getAcceptedTourByTourId(Long tourId) {
        return guideTourRepository.findByTourIdAndStatus(tourId, "ACCEPTED");
    }
    
    public List<GuideTour> getAllTours() {
        return guideTourRepository.findAll();
    }
    
    public List<GuideTour> getToursByStatus(String status) {
        return guideTourRepository.findByStatus(status);
    }
    
    public List<GuideTour> getToursByDate(LocalDate date) {
        return guideTourRepository.findByTourDate(date);
    }
    
    public List<GuideTour> getToursByGuideIdAndDate(Long guideId, LocalDate date) {
        return guideTourRepository.findByGuideIdAndTourDate(guideId, date);
    }
    
    @Transactional
    public void deleteToursByTourId(Long tourId) {
        guideTourRepository.deleteByTourId(tourId);
    }
}
