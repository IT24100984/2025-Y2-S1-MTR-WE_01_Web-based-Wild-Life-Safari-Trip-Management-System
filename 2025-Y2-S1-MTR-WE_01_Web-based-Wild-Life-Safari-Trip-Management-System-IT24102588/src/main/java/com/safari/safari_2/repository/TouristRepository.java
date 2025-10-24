package com.safari.safari_2.repository;

import com.safari.safari_2.model.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TouristRepository extends JpaRepository<Tourist, Long> {
    
    // Find tourist by user ID
    Optional<Tourist> findByUserId(Long userId);
    
    // Find tourists by nationality
    List<Tourist> findByNationality(String nationality);
}
