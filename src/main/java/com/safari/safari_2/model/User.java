// src/main/java/com/safari/safari_2/model/User.java
package com.safari.safari_2.model;

import com.safari.safari_2.enums.Role;
import com.safari.safari_2.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String contactNumber;

    @Column(unique = true, nullable = false)
    private String nic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Tourist-specific
    private String nationality;

    // Guide/Driver-specific
    private Integer experience;

    private String languages; // comma-separated

    // Driver-specific
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}