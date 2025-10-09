-- Admin User Setup Script
-- This script creates the admin user with the correct BCrypt encrypted password

-- Step 1: Delete any existing admin user to avoid conflicts
DELETE FROM users WHERE username = 'admin';

-- Step 2: Insert admin user with BCrypt encrypted password
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES (
    'admin', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 
    'ADMIN', 
    'Admin', 
    'admin', 
    'admin@gmail.com', 
    '07188888888', 
    '200479504848'
);

-- Step 3: Verify the admin user was created
SELECT username, role, first_name, last_name, email FROM users WHERE username = 'admin';

-- Login Credentials:
-- Username: admin
-- Password: admin123
-- Role: ADMIN
-- Email: admin@gmail.com
-- Contact: 07188888888
-- NIC: 200479504848




