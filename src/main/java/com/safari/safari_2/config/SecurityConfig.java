package com.safari.safari_2.config;

import com.safari.safari_2.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/static/**", "/api/users/**", "/api/tours/**", "/api/tourist-users/**", "/api/tourist-bookings/**", "/api/guide-bookings/**", "/api/driver-bookings/**", "/api/driver-tours/**", "/api/guide-tours/**", "/api/tour-assignments/**", "/api/test/**", "/api/test-tours/**", "/api/session/**", "/api/drivers/**", "/api/guides/**", "/api/admin/**", "/api/reviews/**", "/api/notices", "/api/notices/**", "/api/contact-details", "/api/contact-details/**", "/signup", "/login", "/home", "/explore-tour", "/yala-tour", "/wilpattu-tour", "/udawalawe-tour", "/minneriya-tour", "/kumana-tour", "/sinharaja-tour", "/driver-dashboard", "/guide-dashboard", "/tourist-dashboard", "/admin-dashboard", "/reviews").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/home")
                .permitAll()
            )
            .userDetailsService(userDetailsService)
            .csrf(csrf -> csrf.disable()); // Disable CSRF completely for now

        return http.build();
    }
}