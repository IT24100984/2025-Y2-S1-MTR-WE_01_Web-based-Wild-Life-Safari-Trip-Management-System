package com.safari.safari_2.controller;

import com.safari.safari_2.model.TouristBooking;
import com.safari.safari_2.service.TouristBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tourist-bookings")
public class TouristBookingController {
    
    @Autowired
    private TouristBookingService touristBookingService;
    
    @PostMapping
    public TouristBooking createBooking(@RequestBody TouristBookingRequest request) {
        return touristBookingService.createBooking(
            request.getTouristId(),
            request.getTourName(),
            request.getDate(),
            request.getNumberOfPeople(),
            request.getSpecialInstruction()
        );
    }
    
    @GetMapping
    public List<TouristBooking> getAllBookings() {
        return touristBookingService.getAllBookings();
    }
    
    @GetMapping("/tourist/{touristId}")
    public List<TouristBooking> getBookingsByTouristId(@PathVariable Long touristId) {
        return touristBookingService.getBookingsByTouristId(touristId);
    }
    
    @GetMapping("/booking/{bookingId}")
    public Optional<TouristBooking> getBookingByBookingId(@PathVariable String bookingId) {
        return touristBookingService.getBookingByBookingId(bookingId);
    }
    
    @GetMapping("/status/{status}")
    public List<TouristBooking> getBookingsByStatus(@PathVariable String status) {
        return touristBookingService.getBookingsByStatus(status);
    }
    
    @GetMapping("/tourist/{touristId}/status/{status}")
    public List<TouristBooking> getBookingsByTouristIdAndStatus(@PathVariable Long touristId, @PathVariable String status) {
        return touristBookingService.getBookingsByTouristIdAndStatus(touristId, status);
    }
    
    @GetMapping("/date/{date}")
    public List<TouristBooking> getBookingsByDate(@PathVariable String date) {
        return touristBookingService.getBookingsByDate(LocalDate.parse(date));
    }
    
    @GetMapping("/tourist/{touristId}/date/{date}")
    public List<TouristBooking> getBookingsByTouristIdAndDate(@PathVariable Long touristId, @PathVariable String date) {
        return touristBookingService.getBookingsByTouristIdAndDate(touristId, LocalDate.parse(date));
    }
    
    @GetMapping("/tour/{tourName}")
    public List<TouristBooking> getBookingsByTourName(@PathVariable String tourName) {
        return touristBookingService.getBookingsByTourName(tourName);
    }
    
    @GetMapping("/tourist/{touristId}/tour/{tourName}")
    public List<TouristBooking> getBookingsByTouristIdAndTourName(@PathVariable Long touristId, @PathVariable String tourName) {
        return touristBookingService.getBookingsByTouristIdAndTourName(touristId, tourName);
    }
    
    @PutMapping("/{bookingId}/status")
    public TouristBooking updateBookingStatus(@PathVariable String bookingId, @RequestBody StatusUpdateRequest request) {
        return touristBookingService.updateBookingStatus(bookingId, request.getStatus());
    }
    
    @PutMapping("/{bookingId}/action")
    public TouristBooking updateBookingAction(@PathVariable String bookingId, @RequestBody ActionUpdateRequest request) {
        return touristBookingService.updateBookingAction(bookingId, request.getAction());
    }
    
    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable String bookingId) {
        touristBookingService.deleteBooking(bookingId);
    }
    
    // Request DTOs
    public static class TouristBookingRequest {
        private Long touristId;
        private String tourName;
        private LocalDate date;
        private Integer numberOfPeople;
        private String specialInstruction;
        
        // Getters and Setters
        public Long getTouristId() { return touristId; }
        public void setTouristId(Long touristId) { this.touristId = touristId; }
        public String getTourName() { return tourName; }
        public void setTourName(String tourName) { this.tourName = tourName; }
        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }
        public Integer getNumberOfPeople() { return numberOfPeople; }
        public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }
        public String getSpecialInstruction() { return specialInstruction; }
        public void setSpecialInstruction(String specialInstruction) { this.specialInstruction = specialInstruction; }
    }
    
    public static class StatusUpdateRequest {
        private String status;
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
    
    public static class ActionUpdateRequest {
        private String action;
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
    }
}






