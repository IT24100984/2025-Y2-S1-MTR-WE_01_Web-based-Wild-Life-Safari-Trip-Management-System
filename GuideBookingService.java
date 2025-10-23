package com.safari.safari_2.service;

import com.safari.safari_2.model.GuideBooking;
import com.safari.safari_2.repository.GuideBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GuideBookingService {
    
    @Autowired
    private GuideBookingRepository guideBookingRepository;
    
    public GuideBooking createBooking(Long guideId, String tourName, LocalDate date, 
                                     Integer numberOfPeople, String specialInstruction) {
        GuideBooking booking = new GuideBooking(guideId, tourName, date, numberOfPeople, specialInstruction);
        return guideBookingRepository.save(booking);
    }
    
    public List<GuideBooking> getAllBookings() {
        return guideBookingRepository.findAll();
    }
    
    public List<GuideBooking> getBookingsByGuideId(Long guideId) {
        return guideBookingRepository.findByGuideId(guideId);
    }
    
    public Optional<GuideBooking> getBookingByBookingId(String bookingId) {
        return guideBookingRepository.findByBookingId(bookingId);
    }
    
    public List<GuideBooking> getBookingsByStatus(String status) {
        return guideBookingRepository.findByStatus(status);
    }
    
    public List<GuideBooking> getBookingsByGuideIdAndStatus(Long guideId, String status) {
        return guideBookingRepository.findByGuideIdAndStatus(guideId, status);
    }
    
    public List<GuideBooking> getBookingsByDate(LocalDate date) {
        return guideBookingRepository.findByDate(date);
    }
    
    public List<GuideBooking> getBookingsByGuideIdAndDate(Long guideId, LocalDate date) {
        return guideBookingRepository.findByGuideIdAndDate(guideId, date);
    }
    
    public List<GuideBooking> getBookingsByTourName(String tourName) {
        return guideBookingRepository.findByTourName(tourName);
    }
    
    public List<GuideBooking> getBookingsByGuideIdAndTourName(Long guideId, String tourName) {
        return guideBookingRepository.findByGuideIdAndTourName(guideId, tourName);
    }
    
    public GuideBooking updateBookingStatus(String bookingId, String status) {
        Optional<GuideBooking> bookingOptional = guideBookingRepository.findByBookingId(bookingId);
        if (bookingOptional.isPresent()) {
            GuideBooking booking = bookingOptional.get();
            booking.setStatus(status);
            return guideBookingRepository.save(booking);
        }
        return null;
    }
    
    public GuideBooking updateBookingAction(String bookingId, String action) {
        Optional<GuideBooking> bookingOptional = guideBookingRepository.findByBookingId(bookingId);
        if (bookingOptional.isPresent()) {
            GuideBooking booking = bookingOptional.get();
            booking.setAction(action);
            return guideBookingRepository.save(booking);
        }
        return null;
    }
    
    public void deleteBooking(String bookingId) {
        Optional<GuideBooking> bookingOptional = guideBookingRepository.findByBookingId(bookingId);
        if (bookingOptional.isPresent()) {
            guideBookingRepository.delete(bookingOptional.get());
        }
    }
    
    public void deleteBookingsByTourId(Long tourId) {
        // This method can be implemented if needed for tour-specific deletions
        // For now, we'll leave it empty as the main deletion is handled by tour deletion
    }
}
