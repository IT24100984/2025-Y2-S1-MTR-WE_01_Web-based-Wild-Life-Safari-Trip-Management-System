package com.safari.safari_2.controller;

import com.safari.safari_2.model.DriverBooking;
import com.safari.safari_2.service.DriverBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/driver-bookings")
public class DriverBookingController {
    
    @Autowired
    private DriverBookingService driverBookingService;
    
    @PostMapping
    public DriverBooking createBooking(@RequestBody DriverBookingRequest request) {
        return driverBookingService.createBooking(
            request.getDriverId(),
            request.getTourName(),
            request.getDate(),
            request.getNumberOfPeople(),
            request.getSpecialInstruction()
        );
    }
    
    @GetMapping
    public List<DriverBooking> getAllBookings() {
        return driverBookingService.getAllBookings();
    }
    
    @GetMapping("/driver/{driverId}")
    public List<DriverBooking> getBookingsByDriverId(@PathVariable Long driverId) {
        return driverBookingService.getBookingsByDriverId(driverId);
    }
    
    @GetMapping("/booking/{bookingId}")
    public Optional<DriverBooking> getBookingByBookingId(@PathVariable String bookingId) {
        return driverBookingService.getBookingByBookingId(bookingId);
    }
    
    @GetMapping("/status/{status}")
    public List<DriverBooking> getBookingsByStatus(@PathVariable String status) {
        return driverBookingService.getBookingsByStatus(status);
    }
    
    @GetMapping("/driver/{driverId}/status/{status}")
    public List<DriverBooking> getBookingsByDriverIdAndStatus(@PathVariable Long driverId, @PathVariable String status) {
        return driverBookingService.getBookingsByDriverIdAndStatus(driverId, status);
    }
    
    @GetMapping("/date/{date}")
    public List<DriverBooking> getBookingsByDate(@PathVariable String date) {
        return driverBookingService.getBookingsByDate(LocalDate.parse(date));
    }
    
    @GetMapping("/driver/{driverId}/date/{date}")
    public List<DriverBooking> getBookingsByDriverIdAndDate(@PathVariable Long driverId, @PathVariable String date) {
        return driverBookingService.getBookingsByDriverIdAndDate(driverId, LocalDate.parse(date));
    }
    
    @GetMapping("/tour/{tourName}")
    public List<DriverBooking> getBookingsByTourName(@PathVariable String tourName) {
        return driverBookingService.getBookingsByTourName(tourName);
    }
    
    @GetMapping("/driver/{driverId}/tour/{tourName}")
    public List<DriverBooking> getBookingsByDriverIdAndTourName(@PathVariable Long driverId, @PathVariable String tourName) {
        return driverBookingService.getBookingsByDriverIdAndTourName(driverId, tourName);
    }
    
    @PutMapping("/{bookingId}/status")
    public DriverBooking updateBookingStatus(@PathVariable String bookingId, @RequestBody StatusUpdateRequest request) {
        return driverBookingService.updateBookingStatus(bookingId, request.getStatus());
    }
    
    @PutMapping("/{bookingId}/action")
    public DriverBooking updateBookingAction(@PathVariable String bookingId, @RequestBody ActionUpdateRequest request) {
        return driverBookingService.updateBookingAction(bookingId, request.getAction());
    }
    
    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable String bookingId) {
        driverBookingService.deleteBooking(bookingId);
    }
    
    // Request DTOs
    public static class DriverBookingRequest {
        private Long driverId;
        private String tourName;
        private LocalDate date;
        private Integer numberOfPeople;
        private String specialInstruction;
        
        // Getters and Setters
        public Long getDriverId() { return driverId; }
        public void setDriverId(Long driverId) { this.driverId = driverId; }
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






