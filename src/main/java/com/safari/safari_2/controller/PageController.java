package com.safari.safari_2.controller;

import com.safari.safari_2.model.User;
import com.safari.safari_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        // Find user in database
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("username", user.getUsername());
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("lastName", user.getLastName());
            model.addAttribute("role", user.getRole().name());
            model.addAttribute("email", user.getEmail());
        } else {
            // Fallback if user not found
            model.addAttribute("username", username);
            model.addAttribute("firstName", "User");
            model.addAttribute("lastName", "");
            model.addAttribute("role", "USER");
            model.addAttribute("email", "");
        }
        
        return "touristdashboard";
    }
    
    @GetMapping("/explore-tour")
    public String exploreTour() {
        return "explore tour";
    }
    
    @GetMapping("/yala-tour")
    public String yalaTour() {
        return "yala_tour";
    }
    
    @GetMapping("/wilpattu-tour")
    public String wilpattuTour() {
        return "wilpattu_tour";
    }
    
    @GetMapping("/udawalawe-tour")
    public String udawalaweTour() {
        return "udawalawe_tour";
    }
    
    @GetMapping("/minneriya-tour")
    public String minneriyaTour() {
        return "minneriya_tour";
    }
    
    @GetMapping("/kumana-tour")
    public String kumanaTour() {
        return "kumana_tour";
    }
    
    @GetMapping("/sinharaja-tour")
    public String sinharajaTour() {
        return "sinharaja_tour";
    }
    
    @GetMapping("/driver-dashboard")
    public String driverDashboard() {
        return "driverdashboard"; // Maps to driverdashboard.html template
    }
    
    @GetMapping("/guide-dashboard")
    public String guideDashboard() {
        return "guidedashboard"; // Maps to guidedashboard.html template
    }
    
    @GetMapping("/tourist-dashboard")
    public String touristDashboard() {
        return "touristdashboard"; // Maps to touristdashboard.html template
    }
    
    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin dashboard"; // Maps to admin dashboard.html template
    }

    @GetMapping("/reviews")
    public String reviews() {
        return "reviews"; // Maps to reviews.html template
    }
}
