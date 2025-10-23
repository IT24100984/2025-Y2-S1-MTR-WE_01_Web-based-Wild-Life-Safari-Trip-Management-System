package com.safari.safari_2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "guide_bookings")
public class GuideBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "booking_id", nullable = false, unique = true)
    private String bookingId;
    
    @Column(name = "guide_id", nullable = false)
    private Long guideId;
    
    @Column(name = "tour_name", nullable = false)
    private String tourName;
    
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Column(name = "number_of_people", nullable = false)
    private Integer numberOfPeople;
    
    @Column(name = "special_instruction", columnDefinition = "TEXT")
    private String specialInstruction;
    
    @Column(name = "status", nullable = false)
    private String status = "PENDING"; // PENDING, CONFIRMED, CANCELLED, COMPLETED
    
    @Column(name = "action", columnDefinition = "TEXT")
    private String action;
    
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;
    
    // Constructors
    public GuideBooking() {}
    
    public GuideBooking(Long guideId, String tourName, LocalDate date, 
                        Integer numberOfPeople, String specialInstruction) {
        this.guideId = guideId;
        this.tourName = tourName;
        this.date = date;
        this.numberOfPeople = numberOfPeople;
        this.specialInstruction = specialInstruction;
        this.bookingId = generateBookingId();
        this.createdDate = LocalDate.now();
    }
    
    private String generateBookingId() {
        return "GB" + System.currentTimeMillis() + "_" + guideId;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
    
    public Long getGuideId() {
        return guideId;
    }
    
    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }
    
    public String getTourName() {
        return tourName;
    }
    
    public void setTourName(String tourName) {
        this.tourName = tourName;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }
    
    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
    
    public String getSpecialInstruction() {
        return specialInstruction;
    }
    
    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}






