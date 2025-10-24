package com.safari.safari_2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "driver_tours")
public class DriverTour {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "driver_id", nullable = false)
    private Long driverId;
    
    @Column(name = "tour_id", nullable = false)
    private Long tourId;
    
    @Column(name = "tour_name", nullable = false)
    private String tourName;
    
    @Column(name = "tour_date", nullable = false)
    private LocalDate tourDate;
    
    @Column(name = "number_of_people", nullable = false)
    private Integer numberOfPeople;
    
    @Column(name = "special_instructions", columnDefinition = "TEXT")
    private String specialInstructions;
    
    @Column(name = "tourist_id", nullable = false)
    private Long touristId;
    
    @Column(name = "tourist_name", nullable = false)
    private String touristName;
    
    @Column(name = "tourist_contact", nullable = false)
    private String touristContact;
    
    @Column(name = "status", nullable = false)
    private String status = "AVAILABLE"; // AVAILABLE, ACCEPTED, COMPLETED, CANCELLED
    
    @Column(name = "accepted_date")
    private LocalDate acceptedDate;
    
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;
    
    // Constructors
    public DriverTour() {}
    
    public DriverTour(Long driverId, Long tourId, String tourName, LocalDate tourDate, 
                     Integer numberOfPeople, String specialInstructions, Long touristId, 
                     String touristName, String touristContact) {
        this.driverId = driverId;
        this.tourId = tourId;
        this.tourName = tourName;
        this.tourDate = tourDate;
        this.numberOfPeople = numberOfPeople;
        this.specialInstructions = specialInstructions;
        this.touristId = touristId;
        this.touristName = touristName;
        this.touristContact = touristContact;
        this.createdDate = LocalDate.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getDriverId() {
        return driverId;
    }
    
    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
    
    public Long getTourId() {
        return tourId;
    }
    
    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }
    
    public String getTourName() {
        return tourName;
    }
    
    public void setTourName(String tourName) {
        this.tourName = tourName;
    }
    
    public LocalDate getTourDate() {
        return tourDate;
    }
    
    public void setTourDate(LocalDate tourDate) {
        this.tourDate = tourDate;
    }
    
    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }
    
    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    
    public Long getTouristId() {
        return touristId;
    }
    
    public void setTouristId(Long touristId) {
        this.touristId = touristId;
    }
    
    public String getTouristName() {
        return touristName;
    }
    
    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }
    
    public String getTouristContact() {
        return touristContact;
    }
    
    public void setTouristContact(String touristContact) {
        this.touristContact = touristContact;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDate getAcceptedDate() {
        return acceptedDate;
    }
    
    public void setAcceptedDate(LocalDate acceptedDate) {
        this.acceptedDate = acceptedDate;
    }
    
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}






