-- Create Admin User with username 'adminw'
-- This script creates an admin user with username 'adminw' and password 'admin123'

-- Insert adminw user
INSERT INTO users (username, password, first_name, last_name, email, contact_number, nic, role) 
VALUES ('adminw', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Admin', 'User', 'adminw@safari.com', '0000000001', '000000001V', 'ADMIN');

-- Verify adminw user was created
SELECT user_id, username, first_name, last_name, email, role 
FROM users 
WHERE username = 'adminw';

PRINT 'Admin user created successfully!';
PRINT 'Username: adminw';
PRINT 'Password: admin123';
PRINT 'Role: ADMIN';
