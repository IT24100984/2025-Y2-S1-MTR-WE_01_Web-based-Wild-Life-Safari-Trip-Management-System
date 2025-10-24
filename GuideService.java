package com.safari.safari_2.service;

import com.safari.safari_2.model.Guide;
import com.safari.safari_2.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuideService {
    
    @Autowired
    private GuideRepository guideRepository;
    
    public Guide createGuide(Long userId, Integer experienceYears, String languages, 
                            String specializations, String description) {
        Guide guide = new Guide(userId, experienceYears, languages, specializations, description);
        return guideRepository.save(guide);
    }
    
    public List<Guide> getAllGuides() {
        return guideRepository.findAll();
    }
    
    public Optional<Guide> getGuideByUserId(Long userId) {
        return guideRepository.findByUserId(userId);
    }
    
    public List<Guide> getAvailableGuides() {
        return guideRepository.findByIsAvailableTrue();
    }
    
    public List<Guide> getGuidesByExperience(Integer minExperience) {
        return guideRepository.findByExperienceYearsGreaterThanEqualAndIsAvailableTrue(minExperience);
    }
    
    public Guide updateGuideDescription(Long userId, String description) {
        Optional<Guide> guideOptional = guideRepository.findByUserId(userId);
        if (guideOptional.isPresent()) {
            Guide guide = guideOptional.get();
            guide.setDescription(description);
            return guideRepository.save(guide);
        }
        return null;
    }
    
    public Guide updateGuideAvailability(Long userId, Boolean isAvailable) {
        Optional<Guide> guideOptional = guideRepository.findByUserId(userId);
        if (guideOptional.isPresent()) {
            Guide guide = guideOptional.get();
            guide.setIsAvailable(isAvailable);
            return guideRepository.save(guide);
        }
        return null;
    }
    
    public void deleteGuideByUserId(Long userId) {
        Optional<Guide> guideOptional = guideRepository.findByUserId(userId);
        if (guideOptional.isPresent()) {
            guideRepository.delete(guideOptional.get());
        }
    }
}
