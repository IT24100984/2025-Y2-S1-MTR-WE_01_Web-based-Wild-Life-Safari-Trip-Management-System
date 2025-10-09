// src/main/java/com/safari/safari_2/dto/RegisterDTO.java
package com.safari.safari_2.dto;

import com.safari.safari_2.enums.Role;
import com.safari.safari_2.enums.VehicleType;
import lombok.Data;

@Data
public class RegisterDTO {

    private String username;
    private String password;
    private String confirmPassword; // For validation, not stored
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String nic;
    private Role role;

    // Tourist
    private String nationality;

    // Guide/Driver
    private Integer experience;
    private String language; // Note: in JS it's 'language', but we'll map to 'languages' in entity

    // Driver
    private String licenseNumber;
    private VehicleType vehicleType;
}