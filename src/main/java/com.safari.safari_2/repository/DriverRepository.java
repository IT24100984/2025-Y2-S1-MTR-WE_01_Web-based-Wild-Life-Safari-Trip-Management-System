package com.safari.safari_2.repository;

import com.safari.safari_2.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    
    // Find driver by user ID
    Optional<Driver> findByUserId(Long userId);
    
    // Find available drivers
    List<Driver> findByIsAvailableTrue();
    
    // Find drivers by vehicle type
    List<Driver> findByVehicleType(String vehicleType);
    
    // Find available drivers by vehicle type
    List<Driver> findByVehicleTypeAndIsAvailableTrue(String vehicleType);
}






