package com.safari.safari_2.controller;

import com.safari.safari_2.model.TouristUser;
import com.safari.safari_2.service.TouristUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tourist-users")
public class TouristUserController {
    
    @Autowired
    private TouristUserService touristUserService;
    
    @GetMapping
    public List<TouristUser> getAllTouristUsers() {
        return touristUserService.getAllTouristUsers();
    }
    
    @GetMapping("/user/{userId}")
    public Optional<TouristUser> getTouristUserByUserId(@PathVariable Long userId) {
        return touristUserService.getTouristUserByUserId(userId);
    }
    
    @GetMapping("/nationality/{nationality}")
    public List<TouristUser> getTouristUsersByNationality(@PathVariable String nationality) {
        return touristUserService.getTouristUsersByNationality(nationality);
    }
    
    @GetMapping("/email/{email}")
    public Optional<TouristUser> getTouristUserByEmail(@PathVariable String email) {
        return touristUserService.getTouristUserByEmail(email);
    }
    
    @GetMapping("/username/{username}")
    public Optional<TouristUser> getTouristUserByUsername(@PathVariable String username) {
        return touristUserService.getTouristUserByUsername(username);
    }
    
    @GetMapping("/nic/{nic}")
    public Optional<TouristUser> getTouristUserByNic(@PathVariable String nic) {
        return touristUserService.getTouristUserByNic(nic);
    }
}




