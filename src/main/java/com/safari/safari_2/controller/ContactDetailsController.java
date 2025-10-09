package com.safari.safari_2.controller;

import com.safari.safari_2.model.ContactDetails;
import com.safari.safari_2.service.ContactDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contact-details")
public class ContactDetailsController {

    @Autowired
    private ContactDetailsService contactDetailsService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getContactDetails() {
        try {
            ContactDetails contactDetails = contactDetailsService.getContactDetails();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("address", contactDetails.getAddress());
            response.put("phone", contactDetails.getPhone());
            response.put("email", contactDetails.getEmail());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to get contact details: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateContactDetails(@RequestBody Map<String, Object> request) {
        try {
            String address = (String) request.get("address");
            String phone = (String) request.get("phone");
            String email = (String) request.get("email");

            if (address == null || address.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Address cannot be empty");
                return ResponseEntity.badRequest().body(response);
            }

            if (phone == null || phone.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Phone number cannot be empty");
                return ResponseEntity.badRequest().body(response);
            }

            if (email == null || email.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Email cannot be empty");
                return ResponseEntity.badRequest().body(response);
            }

            ContactDetails updatedContactDetails = contactDetailsService.updateContactDetails(address, phone, email);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Contact details updated successfully");
            response.put("contactDetails", updatedContactDetails);
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update contact details: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
