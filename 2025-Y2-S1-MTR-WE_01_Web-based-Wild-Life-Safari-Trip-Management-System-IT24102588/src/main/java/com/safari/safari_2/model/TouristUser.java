package com.safari.safari_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tourist_users")
public class TouristUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "nationality", nullable = false)
    private String nationality;
    
    @Column(name = "nic", nullable = false)
    private String nic;
    
    @Column(name = "username", nullable = false)
    private String username;
    
    // Constructors
    public TouristUser() {}
    
    public TouristUser(Long userId, String email, String nationality, String nic, String username) {
        this.userId = userId;
        this.email = email;
        this.nationality = nationality;
        this.nic = nic;
        this.username = username;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNationality() {
        return nationality;
    }
    
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    public String getNic() {
        return nic;
    }
    
    public void setNic(String nic) {
        this.nic = nic;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}




