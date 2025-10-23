package com.safari.safari_2.service;

import com.safari.safari_2.model.DriverBooking;
import com.safari.safari_2.repository.DriverBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DriverBookingService {
    
    @Autowired
    private DriverBookingRepository driverBookingRepository;
    
    public DriverBooking createBooking(Long driverId, String tourName, LocalDate date, 
                                      Integer numberOfPeople, String specialInstruction) {
        DriverBooking booking = new DriverBooking(driverId, tourName, date, numberOfPeople, specialInstruction);
        return driverBookingRepository.save(booking);
    }
    
    public List<DriverBooking> getAllBookings() {
        return driverBookingRepository.findAll();
    }
    
    public List<DriverBooking> getBookingsByDriverId(Long driverId) {
        return driverBookingRepository.findByDriverId(driverId);
    }
    
    public Optional<DriverBooking> getBookingByBookingId(String bookingId) {
        return driverBookingRepository.findByBookingId(bookingId);
    }
    
    public List<DriverBooking> getBookingsByStatus(String status) {
        return driverBookingRepository.findByStatus(status);
    }
    
    public List<DriverBooking> getBookingsByDriverIdAndStatus(Long driverId, String status) {
        return driverBookingRepository.findByDriverIdAndStatus(driverId, status);
    }
    
    public List<DriverBooking> getBookingsByDate(LocalDate date) {
        return driverBookingRepository.findByDate(date);
    }
    
    public List<DriverBooking> getBookingsByDriverIdAndDate(Long driverId, LocalDate date) {
        return driverBookingRepository.findByDriverIdAndDate(driverId, date);
    }
    
    public List<DriverBooking> getBookingsByTourName(String tourName) {
        return driverBookingRepository.findByTourName(tourName);
    }
    
    public List<DriverBooking> getBookingsByDriverIdAndTourName(Long driverId, String tourName) {
        return driverBookingRepository.findByDriverIdAndTourName(driverId, tourName);
    }
    
    public DriverBooking updateBookingStatus(String bookingId, String status) {
        Optional<DriverBooking> bookingOptional = driverBookingRepository.findByBookingId(bookingId);
        if (bookingOptional.isPresent()) {
            DriverBooking booking = bookingOptional.get();
            booking.setStatus(status);
            return driverBookingRepository.save(booking);
        }
        return null;
    }
    
    public DriverBooking updateBookingAction(String bookingId, String action) {
        Optional<DriverBooking> bookingOptional = driverBookingRepository.findByBookingId(bookingId);
        if (bookingOptional.isPresent()) {
            DriverBooking booking = bookingOptional.get();
            booking.setAction(action);
            return driverBookingRepository.save(booking);
        }
        return null;
    }
    
    public void deleteBooking(String bookingId) {
        Optional<DriverBooking> bookingOptional = driverBookingRepository.findByBookingId(bookingId);
        if (bookingOptional.isPresent()) {
            driverBookingRepository.delete(bookingOptional.get());
        }
    }
    
    public void deleteBookingsByTourId(Long tourId) {
        // This method can be implemented if needed for tour-specific deletions
        // For now, we'll leave it empty as the main deletion is handled by tour deletion
    }
}
