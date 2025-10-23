package com.safari.safari_2.controller;

import com.safari.safari_2.model.GuideBooking;
import com.safari.safari_2.service.GuideBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/guide-bookings")
public class GuideBookingController {
    
    @Autowired
    private GuideBookingService guideBookingService;
    
    @PostMapping
    public GuideBooking createBooking(@RequestBody GuideBookingRequest request) {
        return guideBookingService.createBooking(
            request.getGuideId(),
            request.getTourName(),
            request.getDate(),
            request.getNumberOfPeople(),
            request.getSpecialInstruction()
        );
    }
    
    @GetMapping
    public List<GuideBooking> getAllBookings() {
        return guideBookingService.getAllBookings();
    }
    
    @GetMapping("/guide/{guideId}")
    public List<GuideBooking> getBookingsByGuideId(@PathVariable Long guideId) {
        return guideBookingService.getBookingsByGuideId(guideId);
    }
    
    @GetMapping("/booking/{bookingId}")
    public Optional<GuideBooking> getBookingByBookingId(@PathVariable String bookingId) {
        return guideBookingService.getBookingByBookingId(bookingId);
    }
    
    @GetMapping("/status/{status}")
    public List<GuideBooking> getBookingsByStatus(@PathVariable String status) {
        return guideBookingService.getBookingsByStatus(status);
    }
    
    @GetMapping("/guide/{guideId}/status/{status}")
    public List<GuideBooking> getBookingsByGuideIdAndStatus(@PathVariable Long guideId, @PathVariable String status) {
        return guideBookingService.getBookingsByGuideIdAndStatus(guideId, status);
    }
    
    @GetMapping("/date/{date}")
    public List<GuideBooking> getBookingsByDate(@PathVariable String date) {
        return guideBookingService.getBookingsByDate(LocalDate.parse(date));
    }
    
    @GetMapping("/guide/{guideId}/date/{date}")
    public List<GuideBooking> getBookingsByGuideIdAndDate(@PathVariable Long guideId, @PathVariable String date) {
        return guideBookingService.getBookingsByGuideIdAndDate(guideId, LocalDate.parse(date));
    }
    
    @GetMapping("/tour/{tourName}")
    public List<GuideBooking> getBookingsByTourName(@PathVariable String tourName) {
        return guideBookingService.getBookingsByTourName(tourName);
    }
    
    @GetMapping("/guide/{guideId}/tour/{tourName}")
    public List<GuideBooking> getBookingsByGuideIdAndTourName(@PathVariable Long guideId, @PathVariable String tourName) {
        return guideBookingService.getBookingsByGuideIdAndTourName(guideId, tourName);
    }
    
    @PutMapping("/{bookingId}/status")
    public GuideBooking updateBookingStatus(@PathVariable String bookingId, @RequestBody StatusUpdateRequest request) {
        return guideBookingService.updateBookingStatus(bookingId, request.getStatus());
    }
    
    @PutMapping("/{bookingId}/action")
    public GuideBooking updateBookingAction(@PathVariable String bookingId, @RequestBody ActionUpdateRequest request) {
        return guideBookingService.updateBookingAction(bookingId, request.getAction());
    }
    
    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable String bookingId) {
        guideBookingService.deleteBooking(bookingId);
    }
    
    // Request DTOs
    public static class GuideBookingRequest {
        private Long guideId;
        private String tourName;
        private LocalDate date;
        private Integer numberOfPeople;
        private String specialInstruction;
        
        // Getters and Setters
        public Long getGuideId() { return guideId; }
        public void setGuideId(Long guideId) { this.guideId = guideId; }
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






