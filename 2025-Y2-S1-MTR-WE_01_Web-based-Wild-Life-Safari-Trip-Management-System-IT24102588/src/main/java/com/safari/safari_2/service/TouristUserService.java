package com.safari.safari_2.service;

import com.safari.safari_2.model.TouristUser;
import com.safari.safari_2.repository.TouristUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TouristUserService {
    
    @Autowired
    private TouristUserRepository touristUserRepository;
    
    public TouristUser createTouristUser(Long userId, String email, String nationality, String nic, String username) {
        TouristUser touristUser = new TouristUser(userId, email, nationality, nic, username);
        return touristUserRepository.save(touristUser);
    }
    
    public List<TouristUser> getAllTouristUsers() {
        return touristUserRepository.findAll();
    }
    
    public Optional<TouristUser> getTouristUserByUserId(Long userId) {
        return touristUserRepository.findByUserId(userId);
    }
    
    public List<TouristUser> getTouristUsersByNationality(String nationality) {
        return touristUserRepository.findByNationality(nationality);
    }
    
    public Optional<TouristUser> getTouristUserByEmail(String email) {
        return touristUserRepository.findByEmail(email);
    }
    
    public Optional<TouristUser> getTouristUserByUsername(String username) {
        return touristUserRepository.findByUsername(username);
    }
    
    public Optional<TouristUser> getTouristUserByNic(String nic) {
        return touristUserRepository.findByNic(nic);
    }
    
    public void deleteTouristUserByUserId(Long userId) {
        Optional<TouristUser> touristUserOptional = touristUserRepository.findByUserId(userId);
        if (touristUserOptional.isPresent()) {
            touristUserRepository.delete(touristUserOptional.get());
        }
    }
}
