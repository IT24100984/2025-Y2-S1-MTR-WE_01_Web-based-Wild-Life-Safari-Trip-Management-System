package com.safari.safari_2.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class to generate BCrypt password hashes
 * Use this to generate the correct password hash for database insertion
 */
public class PasswordGenerator {
    
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Generate hash for 'admin123'
        String password = "admin123";
        String hashedPassword = encoder.encode(password);
        
        System.out.println("Original password: " + password);
        System.out.println("BCrypt hash: " + hashedPassword);
        
        // Verify the hash
        boolean matches = encoder.matches(password, hashedPassword);
        System.out.println("Password matches: " + matches);
        
        // Generate SQL insert statement
        System.out.println("\nSQL Insert Statement:");
        System.out.println("INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic)");
        System.out.println("VALUES ('admin', '" + hashedPassword + "', 'ADMIN', 'Admin', 'admin', 'admin@gmail.com', '07188888888', '200479504848');");
    }
}




