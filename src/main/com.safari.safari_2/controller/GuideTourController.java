package com.safari.safari_2.controller;

import com.safari.safari_2.model.GuideTour;
import com.safari.safari_2.service.GuideTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/guide-tours")
public class GuideTourController {
    
    @Autowired
    private GuideTourService guideTourService;
    
    @GetMapping("/guide/{guideId}")
    public ResponseEntity<List<GuideTour>> getToursByGuideId(@PathVariable Long guideId) {
        List<GuideTour> tours = guideTourService.getToursByGuideId(guideId);
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/guide/{guideId}/available")
    public ResponseEntity<List<GuideTour>> getAvailableToursByGuideId(@PathVariable Long guideId) {
        List<GuideTour> tours = guideTourService.getAvailableToursByGuideId(guideId);
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/guide/{guideId}/accepted")
    public ResponseEntity<List<GuideTour>> getAcceptedToursByGuideId(@PathVariable Long guideId) {
        List<GuideTour> tours = guideTourService.getAcceptedToursByGuideId(guideId);
        return ResponseEntity.ok(tours);
    }
    
    @PostMapping("/accept")
    public ResponseEntity<Map<String, Object>> acceptTour(@RequestBody AcceptTourRequest request) {
        try {
            boolean success = guideTourService.acceptTour(request.getGuideId(), request.getTourId());
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "Tour accepted successfully");
            } else {
                response.put("success", false);
                response.put("message", "Tour is no longer available or already accepted by another guide");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to accept tour: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/tourist/{touristId}")
    public ResponseEntity<List<GuideTour>> getToursByTouristId(@PathVariable Long touristId) {
        List<GuideTour> tours = guideTourService.getToursByTouristId(touristId);
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/tour/{tourId}/accepted")
    public ResponseEntity<GuideTour> getAcceptedTourByTourId(@PathVariable Long tourId) {
        return guideTourService.getAcceptedTourByTourId(tourId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<GuideTour>> getAllTours() {
        List<GuideTour> tours = guideTourService.getAllTours();
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<GuideTour>> getToursByStatus(@PathVariable String status) {
        List<GuideTour> tours = guideTourService.getToursByStatus(status);
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<GuideTour>> getToursByDate(@PathVariable String date) {
        List<GuideTour> tours = guideTourService.getToursByDate(LocalDate.parse(date));
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/guide/{guideId}/date/{date}")
    public ResponseEntity<List<GuideTour>> getToursByGuideIdAndDate(@PathVariable Long guideId, @PathVariable String date) {
        List<GuideTour> tours = guideTourService.getToursByGuideIdAndDate(guideId, LocalDate.parse(date));
        return ResponseEntity.ok(tours);
    }
    
    // Request DTO
    public static class AcceptTourRequest {
        private Long guideId;
        private Long tourId;
        
        public Long getGuideId() { return guideId; }
        public void setGuideId(Long guideId) { this.guideId = guideId; }
        public Long getTourId() { return tourId; }
        public void setTourId(Long tourId) { this.tourId = tourId; }
    }
}




