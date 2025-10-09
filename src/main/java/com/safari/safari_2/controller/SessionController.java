package com.safari.safari_2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    
    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpServletRequest request) {
        Map<String, Object> userInfo = new HashMap<>();
        
        // Get user ID from session
        Object userId = request.getSession().getAttribute("userId");
        Object driverId = request.getSession().getAttribute("driverId");
        Object guideId = request.getSession().getAttribute("guideId");
        
        if (userId != null) {
            userInfo.put("userId", userId);
        }
        
        if (driverId != null) {
            userInfo.put("driverId", driverId);
        }
        
        if (guideId != null) {
            userInfo.put("guideId", guideId);
        }
        
        return ResponseEntity.ok(userInfo);
    }
}




