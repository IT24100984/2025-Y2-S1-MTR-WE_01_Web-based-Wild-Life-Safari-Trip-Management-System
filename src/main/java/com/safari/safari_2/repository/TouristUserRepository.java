package com.safari.safari_2.repository;

import com.safari.safari_2.model.TouristUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TouristUserRepository extends JpaRepository<TouristUser, Long> {
    
    // Find tourist user by user ID
    Optional<TouristUser> findByUserId(Long userId);
    
    // Find tourist users by nationality
    List<TouristUser> findByNationality(String nationality);
    
    // Find tourist user by email
    Optional<TouristUser> findByEmail(String email);
    
    // Find tourist user by username
    Optional<TouristUser> findByUsername(String username);
    
    // Find tourist user by NIC
    Optional<TouristUser> findByNic(String nic);
}




