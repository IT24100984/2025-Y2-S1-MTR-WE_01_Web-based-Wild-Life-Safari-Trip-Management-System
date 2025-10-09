-- Create Admin User
-- This script creates an admin user with username 'admin' and password 'admin123'

-- Insert admin user
INSERT INTO users (username, password, first_name, last_name, email, contact_number, nic, role) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Admin', 'User', 'admin@safari.com', '0000000000', '000000000V', 'ADMIN');

-- Verify admin user was created
SELECT user_id, username, first_name, last_name, email, role 
FROM users 
WHERE username = 'admin';

PRINT 'Admin user created successfully!';
PRINT 'Username: admin';
PRINT 'Password: admin123';
PRINT 'Role: ADMIN';