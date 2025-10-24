package com.safari.safari_2.config;

import com.safari.safari_2.enums.Role;
import com.safari.safari_2.model.User;
import com.safari.safari_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Role userRole = user.getRole();
            
            // Store user ID in session for dashboard access
            request.getSession().setAttribute("userId", user.getId());
            
            // Special handling for admin users (regardless of role)
            if (username.equals("admin") || username.equals("adminw")) {
                // Store admin ID for dashboard
                request.getSession().setAttribute("adminId", user.getId());
                response.sendRedirect("/admin-dashboard");
            }
            // Redirect based on role
            else if (userRole == Role.DRIVER) {
                // Store driver ID for dashboard
                request.getSession().setAttribute("driverId", user.getId());
                response.sendRedirect("/driver-dashboard");
            } else if (userRole == Role.GUIDE) {
                // Store guide ID for dashboard
                request.getSession().setAttribute("guideId", user.getId());
                response.sendRedirect("/guide-dashboard");
            } else if (userRole == Role.TOURIST) {
                // Tourists go to explore tour page
                response.sendRedirect("/explore-tour");
            } else if (userRole == Role.ADMIN) {
                // Store admin ID for dashboard
                request.getSession().setAttribute("adminId", user.getId());
                response.sendRedirect("/admin-dashboard");
            } else {
                // Default fallback
                response.sendRedirect("/explore-tour");
            }
        } else {
            // Fallback if user not found
            response.sendRedirect("/explore-tour");
        }
    }
}
