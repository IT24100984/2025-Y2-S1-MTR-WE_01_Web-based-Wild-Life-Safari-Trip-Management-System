package com.safari.safari_2.controller;

import com.safari.safari_2.model.DriverTour;
import com.safari.safari_2.service.DriverTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/driver-tours")
public class DriverTourController {
    
    @Autowired
    private DriverTourService driverTourService;
    
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<DriverTour>> getToursByDriverId(@PathVariable Long driverId) {
        List<DriverTour> tours = driverTourService.getToursByDriverId(driverId);
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/driver/{driverId}/available")
    public ResponseEntity<List<DriverTour>> getAvailableToursByDriverId(@PathVariable Long driverId) {
        List<DriverTour> tours = driverTourService.getAvailableToursByDriverId(driverId);
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/driver/{driverId}/accepted")
    public ResponseEntity<List<DriverTour>> getAcceptedToursByDriverId(@PathVariable Long driverId) {
        List<DriverTour> tours = driverTourService.getAcceptedToursByDriverId(driverId);
        return ResponseEntity.ok(tours);
    }
    
    @PostMapping("/accept")
    public ResponseEntity<Map<String, Object>> acceptTour(@RequestBody AcceptTourRequest request) {
        try {
            boolean success = driverTourService.acceptTour(request.getDriverId(), request.getTourId());
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "Tour accepted successfully");
            } else {
                response.put("success", false);
                response.put("message", "Tour is no longer available or already accepted by another driver");
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
    public ResponseEntity<List<DriverTour>> getToursByTouristId(@PathVariable Long touristId) {
        List<DriverTour> tours = driverTourService.getToursByTouristId(touristId);
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/tour/{tourId}/accepted")
    public ResponseEntity<DriverTour> getAcceptedTourByTourId(@PathVariable Long tourId) {
        return driverTourService.getAcceptedTourByTourId(tourId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<DriverTour>> getAllTours() {
        List<DriverTour> tours = driverTourService.getAllTours();
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<DriverTour>> getToursByStatus(@PathVariable String status) {
        List<DriverTour> tours = driverTourService.getToursByStatus(status);
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<DriverTour>> getToursByDate(@PathVariable String date) {
        List<DriverTour> tours = driverTourService.getToursByDate(LocalDate.parse(date));
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/driver/{driverId}/date/{date}")
    public ResponseEntity<List<DriverTour>> getToursByDriverIdAndDate(@PathVariable Long driverId, @PathVariable String date) {
        List<DriverTour> tours = driverTourService.getToursByDriverIdAndDate(driverId, LocalDate.parse(date));
        return ResponseEntity.ok(tours);
    }
    
    // Request DTO
    public static class AcceptTourRequest {
        private Long driverId;
        private Long tourId;
        
        public Long getDriverId() { return driverId; }
        public void setDriverId(Long driverId) { this.driverId = driverId; }
        public Long getTourId() { return tourId; }
        public void setTourId(Long tourId) { this.tourId = tourId; }
    }
}






