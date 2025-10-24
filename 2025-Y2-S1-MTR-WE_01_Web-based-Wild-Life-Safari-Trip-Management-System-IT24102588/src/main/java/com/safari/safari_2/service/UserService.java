// src/main/java/com/safari/safari_2/service/UserService.java
package com.safari.safari_2.service;

import com.safari.safari_2.dto.RegisterDTO;
import com.safari.safari_2.enums.Role;
import com.safari.safari_2.model.User;
import com.safari.safari_2.model.Driver;
import com.safari.safari_2.model.Guide;
import com.safari.safari_2.model.Tourist;
import com.safari.safari_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Assume configured in config
    
    @Autowired
    private DriverService driverService;
    
    @Autowired
    private GuideService guideService;
    
    @Autowired
    private TouristService touristService;
    
    @Autowired
    private TouristUserService touristUserService;
    
    @Autowired
    private TourService tourService;

    @Transactional
    public User register(RegisterDTO dto) {
        // Validate passwords match
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Check uniqueness
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.findByNic(dto.getNic()).isPresent()) {
            throw new RuntimeException("NIC already exists");
        }

        // Create user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setContactNumber(dto.getContactNumber());
        user.setNic(dto.getNic());
        user.setRole(dto.getRole());

        // Set role-specific fields
        if (dto.getRole() == Role.TOURIST) {
            if (dto.getNationality() == null || dto.getNationality().isEmpty()) {
                throw new RuntimeException("Nationality is required for tourists");
            }
            user.setNationality(dto.getNationality());
        } else if (dto.getRole() == Role.GUIDE) {
            if (dto.getExperience() == null || dto.getLanguage() == null || dto.getLanguage().isEmpty()) {
                throw new RuntimeException("Experience and languages are required for guides");
            }
            user.setExperience(dto.getExperience());
            user.setLanguages(dto.getLanguage());
        } else if (dto.getRole() == Role.DRIVER) {
            if (dto.getExperience() == null || dto.getLanguage() == null || dto.getLanguage().isEmpty() ||
                    dto.getLicenseNumber() == null || dto.getLicenseNumber().isEmpty() ||
                    dto.getVehicleType() == null) {
                throw new RuntimeException("Experience, languages, license number, and vehicle type are required for drivers");
            }
            user.setExperience(dto.getExperience());
            user.setLanguages(dto.getLanguage());
            user.setLicenseNumber(dto.getLicenseNumber());
            user.setVehicleType(dto.getVehicleType());
        } else {
            throw new RuntimeException("Invalid role");
        }

        User savedUser = userRepository.save(user);
        
        // Create role-specific records
        if (dto.getRole() == Role.DRIVER) {
            driverService.createDriver(
                savedUser.getId(),
                dto.getLicenseNumber(),
                dto.getExperience(),
                dto.getLanguage(),
                "Experienced safari driver with " + dto.getExperience() + " years of expertise."
            );
        } else if (dto.getRole() == Role.GUIDE) {
            guideService.createGuide(
                savedUser.getId(),
                dto.getExperience(),
                dto.getLanguage(),
                "Wildlife tours, Cultural experiences",
                "Professional wildlife guide with " + dto.getExperience() + " years of experience."
            );
        } else if (dto.getRole() == Role.TOURIST) {
            touristService.createTourist(
                savedUser.getId(),
                savedUser.getEmail(),
                dto.getNationality(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getContactNumber(),
                savedUser.getNic(),
                savedUser.getUsername()
            );
            
            // Also create a TouristUser record with only the required fields
            touristUserService.createTouristUser(
                savedUser.getId(),
                savedUser.getEmail(),
                dto.getNationality(),
                savedUser.getNic(),
                savedUser.getUsername()
            );
        }
        
        return savedUser;
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public boolean updateUser(Long userId, Map<String, String> request) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                
                // Update contact number if provided
                if (request.containsKey("contactNumber")) {
                    user.setContactNumber(request.get("contactNumber"));
                }
                
                // Update email if provided
                if (request.containsKey("email")) {
                    user.setEmail(request.get("email"));
                }
                
                userRepository.save(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + e.getMessage());
        }
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                
                // Delete all related data first
                if (user.getRole() == Role.DRIVER) {
                    driverService.deleteDriverByUserId(userId);
                } else if (user.getRole() == Role.GUIDE) {
                    guideService.deleteGuideByUserId(userId);
                } else if (user.getRole() == Role.TOURIST) {
                    touristService.deleteTouristByUserId(userId);
                    touristUserService.deleteTouristUserByUserId(userId);
                }
                
                // Delete all tours created by this user
                tourService.deleteToursByUserId(userId);
                
                // Finally delete the user
                userRepository.delete(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage());
        }
    }
}