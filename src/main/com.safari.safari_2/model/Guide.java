package com.safari.safari_2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "guides")
public class Guide {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "experience_years", nullable = false)
    private Integer experienceYears;
    
    @Column(name = "languages", nullable = false)
    private String languages;
    
    @Column(name = "specializations", columnDefinition = "TEXT")
    private String specializations;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;
    
    @Column(name = "rating")
    private Double rating = 0.0;
    
    @Column(name = "total_tours")
    private Integer totalTours = 0;
    
    @Column(name = "certifications", columnDefinition = "TEXT")
    private String certifications;
    
    @Column(name = "created_date")
    private LocalDate createdDate;
    
    // Constructors
    public Guide() {}
    
    public Guide(Long userId, Integer experienceYears, String languages, 
                 String specializations, String description) {
        this.userId = userId;
        this.experienceYears = experienceYears;
        this.languages = languages;
        this.specializations = specializations;
        this.description = description;
        this.createdDate = LocalDate.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Integer getExperienceYears() {
        return experienceYears;
    }
    
    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
    
    public String getLanguages() {
        return languages;
    }
    
    public void setLanguages(String languages) {
        this.languages = languages;
    }
    
    public String getSpecializations() {
        return specializations;
    }
    
    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public Integer getTotalTours() {
        return totalTours;
    }
    
    public void setTotalTours(Integer totalTours) {
        this.totalTours = totalTours;
    }
    
    public String getCertifications() {
        return certifications;
    }
    
    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }
    
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}




