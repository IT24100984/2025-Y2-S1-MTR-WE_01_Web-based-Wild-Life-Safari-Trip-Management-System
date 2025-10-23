package com.safari.safari_2.service;

import com.safari.safari_2.model.TouristBooking;
import com.safari.safari_2.repository.TouristBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TouristBookingService {
    
    @Autowired
    private TouristBookingRepository touristBookingRepository;
    
    public TouristBooking createBooking(Long touristId, String tourName, LocalDate date, 
                                       Integer numberOfPeople, String specialInstruction) {
        TouristBooking booking = new TouristBooking(touristId, tourName, date, numberOfPeople, specialInstruction);
        return touristBookingRepository.save(booking);
    }
    
    public List<TouristBooking> getAllBookings() {
        return touristBookingRepository.findAll();
    }
    
    public List<TouristBooking> getBookingsByTouristId(Long touristId) {
        return touristBookingRepository.findByTouristId(touristId);
    }
    
    public Optional<TouristBooking> getBookingByBookingId(String bookingId) {
        return touristBookingRepository.findByBookingId(bookingId);
    }
    
    public List<TouristBooking> getBookingsByStatus(String status) {
        return touristBookingRepository.findByStatus(status);
    }
    
    public List<TouristBooking> getBookingsByTouristIdAndStatus(Long touristId, String status) {
        return touristBookingRepository.findByTouristIdAndStatus(touristId, status);
    }
    
    public List<TouristBooking> getBookingsByDate(LocalDate date) {
        return touristBookingRepository.findByDate(date);
    }
    
    public List<TouristBooking> getBookingsByTouristIdAndDate(Long touristId, LocalDate date) {
        return touristBookingRepository.findByTouristIdAndDate(touristId, date);
    }
    
    public List<TouristBooking> getBookingsByTourName(String tourName) {
        return touristBookingRepository.findByTourName(tourName);
    }
    
    public List<TouristBooking> getBookingsByTouristIdAndTourName(Long touristId, String tourName) {
        return touristBookingRepository.findByTouristIdAndTourName(touristId, tourName);
    }
    
    public TouristBooking updateBookingStatus(String bookingId, String status) {
        Optional<TouristBooking> bookingOptional = touristBookingRepository.findByBookingId(bookingId);
        if (bookingOptional.isPresent()) {
            TouristBooking booking = bookingOptional.get();
            booking.setStatus(status);
            return touristBookingRepository.save(booking);
        }
        return null;
    }
    
    public TouristBooking updateBookingAction(String bookingId, String action) {
        Optional<TouristBooking> bookingOptional = touristBookingRepository.findByBookingId(bookingId);
        if (bookingOptional.isPresent()) {
            TouristBooking booking = bookingOptional.get();
            booking.setAction(action);
            return touristBookingRepository.save(booking);
        }
        return null;
    }
    
    public void deleteBooking(String bookingId) {
        Optional<TouristBooking> bookingOptional = touristBookingRepository.findByBookingId(bookingId);
        if (bookingOptional.isPresent()) {
            touristBookingRepository.delete(bookingOptional.get());
        }
    }
}






