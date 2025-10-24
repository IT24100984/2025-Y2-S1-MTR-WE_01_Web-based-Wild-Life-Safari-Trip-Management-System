ackage com.safari.safari_2.service;

import com.safari.safari_2.model.Tourist;
import com.safari.safari_2.repository.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TouristService {
    
    @Autowired
    private TouristRepository touristRepository;
    
    public Tourist createTourist(Long userId, String email, String nationality, String firstName, 
                                String lastName, String contactNumber, String nic, String username) {
        Tourist tourist = new Tourist(userId, email, nationality, firstName, lastName, contactNumber, nic, username);
        return touristRepository.save(tourist);
    }
    
    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }
    
    public Optional<Tourist> getTouristByUserId(Long userId) {
        return touristRepository.findByUserId(userId);
    }
    
    public List<Tourist> getTouristsByNationality(String nationality) {
        return touristRepository.findByNationality(nationality);
    }
    
    public void deleteTouristByUserId(Long userId) {
        Optional<Tourist> touristOptional = touristRepository.findByUserId(userId);
        if (touristOptional.isPresent()) {
            touristRepository.delete(touristOptional.get());
        }
    }
}
