package com.safari.safari_2.repository;

import com.safari.safari_2.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    
    // Find guide by user ID
    Optional<Guide> findByUserId(Long userId);
    
    // Find available guides
    List<Guide> findByIsAvailableTrue();
    
    // Find guides by experience level
    List<Guide> findByExperienceYearsGreaterThanEqual(Integer minExperience);
    
    // Find available guides by experience level
    List<Guide> findByExperienceYearsGreaterThanEqualAndIsAvailableTrue(Integer minExperience);
}




