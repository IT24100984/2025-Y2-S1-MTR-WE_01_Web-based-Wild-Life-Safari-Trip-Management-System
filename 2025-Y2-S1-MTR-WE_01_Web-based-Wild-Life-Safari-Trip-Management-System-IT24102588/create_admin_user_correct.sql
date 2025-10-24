-- Create Admin User Account with Correct BCrypt Password
-- Username: admin
-- Password: admin123 (encrypted with BCrypt)

-- First, delete any existing admin user
DELETE FROM users WHERE username = 'admin';

-- Insert admin user with BCrypt encrypted password
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES ('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', 'Admin', 'admin', 'admin@gmail.com', '07188888888', '200479504848');

-- Note: The password 'admin123' is encrypted using BCrypt
-- The encrypted password is: $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi
-- This is the standard BCrypt hash for the password 'admin123'




