package com.safari.safari_2.controller;

import com.safari.safari_2.dto.RegisterDTO;
import com.safari.safari_2.model.User;
import com.safari.safari_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> request) {
        try {
            // Convert Map to RegisterDTO
            RegisterDTO dto = new RegisterDTO();
            dto.setUsername((String) request.get("username"));
            dto.setPassword((String) request.get("password"));
            dto.setConfirmPassword((String) request.get("confirmPassword"));
            dto.setFirstName((String) request.get("firstName"));
            dto.setLastName((String) request.get("lastName"));
            dto.setEmail((String) request.get("email"));
            dto.setContactNumber((String) request.get("contactNumber"));
            dto.setNic((String) request.get("nic"));
            
            // Set role
            String roleStr = (String) request.get("role");
            if (roleStr != null) {
                dto.setRole(com.safari.safari_2.enums.Role.valueOf(roleStr));
            }
            
            // Set role-specific fields
            if (request.containsKey("nationality")) {
                dto.setNationality((String) request.get("nationality"));
            }
            if (request.containsKey("experience")) {
                dto.setExperience((Integer) request.get("experience"));
            }
            if (request.containsKey("language")) {
                dto.setLanguage((String) request.get("language"));
            }
            if (request.containsKey("licenseNumber")) {
                dto.setLicenseNumber((String) request.get("licenseNumber"));
            }
            if (request.containsKey("vehicleType")) {
                String vehicleTypeStr = (String) request.get("vehicleType");
                if (vehicleTypeStr != null) {
                    dto.setVehicleType(com.safari.safari_2.enums.VehicleType.valueOf(vehicleTypeStr));
                }
            }
            
            User user = userService.register(dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("userId", user.getId());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        try {
            boolean success = userService.updateUser(userId, request);
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "User updated successfully");
            } else {
                response.put("success", false);
                response.put("message", "User not found or could not be updated");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update user: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long userId) {
        try {
            boolean success = userService.deleteUser(userId);
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "User and all related data deleted successfully");
            } else {
                response.put("success", false);
                response.put("message", "User not found or could not be deleted");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to delete user: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}