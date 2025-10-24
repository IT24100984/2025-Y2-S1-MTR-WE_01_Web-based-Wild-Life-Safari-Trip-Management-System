package com.safari.safari_2.service;

import com.safari.safari_2.model.Driver;
import com.safari.safari_2.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {
    
    @Autowired
    private DriverRepository driverRepository;
    
    public Driver createDriver(Long userId, String licenseNumber, Integer experienceYears, 
                              String languages, String description) {
        Driver driver = new Driver(userId, licenseNumber, "Jeep", experienceYears, languages, description);
        return driverRepository.save(driver);
    }
    
    public Driver createDriverWithVehicle(Long userId, String licenseNumber, String vehicleType, 
                                         Integer experienceYears, String languages, String description) {
        Driver driver = new Driver(userId, licenseNumber, vehicleType, experienceYears, languages, description);
        return driverRepository.save(driver);
    }
    
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
    
    public Optional<Driver> getDriverByUserId(Long userId) {
        return driverRepository.findByUserId(userId);
    }
    
    public List<Driver> getAvailableDrivers() {
        return driverRepository.findByIsAvailableTrue();
    }
    
    public List<Driver> getDriversByVehicleType(String vehicleType) {
        return driverRepository.findByVehicleTypeAndIsAvailableTrue(vehicleType);
    }
    
    public Driver updateDriverDescription(Long userId, String description) {
        Optional<Driver> driverOptional = driverRepository.findByUserId(userId);
        if (driverOptional.isPresent()) {
            Driver driver = driverOptional.get();
            driver.setDescription(description);
            return driverRepository.save(driver);
        }
        return null;
    }
    
    public Driver updateDriverAvailability(Long userId, Boolean isAvailable) {
        Optional<Driver> driverOptional = driverRepository.findByUserId(userId);
        if (driverOptional.isPresent()) {
            Driver driver = driverOptional.get();
            driver.setIsAvailable(isAvailable);
            return driverRepository.save(driver);
        }
        return null;
    }
    
    public void deleteDriverByUserId(Long userId) {
        Optional<Driver> driverOptional = driverRepository.findByUserId(userId);
        if (driverOptional.isPresent()) {
            driverRepository.delete(driverOptional.get());
        }
    }
}
