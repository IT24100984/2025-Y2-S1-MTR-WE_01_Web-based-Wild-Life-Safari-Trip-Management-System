package com.safari.safari_2.controller;

import com.safari.safari_2.model.Guide;
import com.safari.safari_2.model.User;
import com.safari.safari_2.repository.GuideRepository;
import com.safari.safari_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/guides")
public class GuideController {
    
    @Autowired
    private GuideRepository guideRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getGuideByUserId(@PathVariable Long userId) {
        try {
            // Get guide record
            Optional<Guide> guideOptional = guideRepository.findByUserId(userId);
            if (!guideOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Guide guide = guideOptional.get();
            
            // Get user record for username
            Optional<User> userOptional = userRepository.findById(userId);
            String username = userOptional.map(User::getUsername).orElse("Guide");
            
            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("id", guide.getId());
            response.put("userId", guide.getUserId());
            response.put("username", username);
            response.put("experienceYears", guide.getExperienceYears());
            response.put("languages", guide.getLanguages());
            response.put("specializations", guide.getSpecializations());
            response.put("description", guide.getDescription());
            response.put("isAvailable", guide.getIsAvailable());
            response.put("rating", guide.getRating());
            response.put("totalTours", guide.getTotalTours());
            response.put("certifications", guide.getCertifications());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch guide information: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping("/{guideId}")
    public ResponseEntity<Map<String, Object>> getGuideById(@PathVariable Long guideId) {
        try {
            Optional<Guide> guideOptional = guideRepository.findById(guideId);
            if (!guideOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Guide guide = guideOptional.get();
            
            // Get user record for username
            Optional<User> userOptional = userRepository.findById(guide.getUserId());
            String username = userOptional.map(User::getUsername).orElse("Guide");
            
            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("id", guide.getId());
            response.put("userId", guide.getUserId());
            response.put("username", username);
            response.put("experienceYears", guide.getExperienceYears());
            response.put("languages", guide.getLanguages());
            response.put("specializations", guide.getSpecializations());
            response.put("description", guide.getDescription());
            response.put("isAvailable", guide.getIsAvailable());
            response.put("rating", guide.getRating());
            response.put("totalTours", guide.getTotalTours());
            response.put("certifications", guide.getCertifications());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch guide information: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @PutMapping("/{guideId}/description")
    public ResponseEntity<Map<String, Object>> updateGuideDescription(@PathVariable Long guideId, @RequestBody Map<String, String> request) {
        try {
            String newDescription = request.get("description");
            if (newDescription == null || newDescription.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Description cannot be empty");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            Optional<Guide> guideOptional = guideRepository.findById(guideId);
            if (!guideOptional.isPresent()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Guide not found");
                return ResponseEntity.notFound().build();
            }
            
            Guide guide = guideOptional.get();
            guide.setDescription(newDescription.trim());
            guideRepository.save(guide);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Description updated successfully");
            response.put("description", guide.getDescription());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to update description: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
