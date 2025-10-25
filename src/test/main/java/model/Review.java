package com.safari.reviewmanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "review", nullable = false, columnDefinition = "TEXT")
    private String review;

    @Column(name = "stars", nullable = false)
    private Integer stars;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    private String status;

    // Constructors
    public Review() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Review(String name, String review, Integer stars) {
        this();
        this.name = name;
        this.review = review;
        this.stars = stars;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}