package com.safari.safari_2.controller;

import com.safari.safari_2.model.Driver;
import com.safari.safari_2.model.User;
import com.safari.safari_2.repository.DriverRepository;
import com.safari.safari_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    
    @Autowired
    private DriverRepository driverRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getDriverByUserId(@PathVariable Long userId) {
        try {
            // Get driver record
            Optional<Driver> driverOptional = driverRepository.findByUserId(userId);
            if (!driverOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Driver driver = driverOptional.get();
            
            // Get user record for username
            Optional<User> userOptional = userRepository.findById(userId);
            String username = userOptional.map(User::getUsername).orElse("Driver");
            
            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("id", driver.getId());
            response.put("userId", driver.getUserId());
            response.put("username", username);
            response.put("licenseNumber", driver.getLicenseNumber());
            response.put("vehicleType", driver.getVehicleType());
            response.put("experienceYears", driver.getExperienceYears());
            response.put("languages", driver.getLanguages());
            response.put("description", driver.getDescription());
            response.put("isAvailable", driver.getIsAvailable());
            response.put("rating", driver.getRating());
            response.put("totalTrips", driver.getTotalTrips());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch driver information: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping("/{driverId}")
    public ResponseEntity<Map<String, Object>> getDriverById(@PathVariable Long driverId) {
        try {
            Optional<Driver> driverOptional = driverRepository.findById(driverId);
            if (!driverOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Driver driver = driverOptional.get();
            
            // Get user record for username
            Optional<User> userOptional = userRepository.findById(driver.getUserId());
            String username = userOptional.map(User::getUsername).orElse("Driver");
            
            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("id", driver.getId());
            response.put("userId", driver.getUserId());
            response.put("username", username);
            response.put("licenseNumber", driver.getLicenseNumber());
            response.put("vehicleType", driver.getVehicleType());
            response.put("experienceYears", driver.getExperienceYears());
            response.put("languages", driver.getLanguages());
            response.put("description", driver.getDescription());
            response.put("isAvailable", driver.getIsAvailable());
            response.put("rating", driver.getRating());
            response.put("totalTrips", driver.getTotalTrips());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch driver information: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @PutMapping("/{driverId}/description")
    public ResponseEntity<Map<String, Object>> updateDriverDescription(@PathVariable Long driverId, @RequestBody Map<String, String> request) {
        try {
            String newDescription = request.get("description");
            if (newDescription == null || newDescription.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Description cannot be empty");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            Optional<Driver> driverOptional = driverRepository.findById(driverId);
            if (!driverOptional.isPresent()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Driver not found");
                return ResponseEntity.notFound().build();
            }
            
            Driver driver = driverOptional.get();
            driver.setDescription(newDescription.trim());
            driverRepository.save(driver);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Description updated successfully");
            response.put("description", driver.getDescription());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to update description: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping("/test/{driverId}")
    public ResponseEntity<Map<String, Object>> testDriverEndpoint(@PathVariable Long driverId) {
        try {
            Optional<Driver> driverOptional = driverRepository.findById(driverId);
            if (!driverOptional.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Driver not found with ID: " + driverId);
                return ResponseEntity.ok(response);
            }
            
            Driver driver = driverOptional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Driver found");
            response.put("driverId", driver.getId());
            response.put("description", driver.getDescription());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}
