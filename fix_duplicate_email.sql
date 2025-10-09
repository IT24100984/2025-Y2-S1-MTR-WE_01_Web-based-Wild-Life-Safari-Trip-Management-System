-- Fix Duplicate Email Constraint Error
-- The error occurs because there's already a user with admin@gmail.com

-- Step 1: Check if there's already a user with admin@gmail.com
SELECT username, email, role FROM users WHERE email = 'admin@gmail.com';

-- Step 2: Check if there's already a user with username 'admin'
SELECT username, email, role FROM users WHERE username = 'admin';

-- Step 3: Delete any existing admin user (by username)
DELETE FROM users WHERE username = 'admin';

-- Step 4: Delete any existing user with admin@gmail.com (if different from username)
DELETE FROM users WHERE email = 'admin@gmail.com' AND username != 'admin';

-- Step 5: Now insert the admin user
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES (
    'admin', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 
    'TOURIST',  -- Use TOURIST role to avoid constraint issues
    'Admin', 
    'admin', 
    'admin@gmail.com', 
    '07188888888', 
    '200479504848'
);

-- Step 6: Try to update to ADMIN role (if constraint allows)
UPDATE users 
SET role = 'ADMIN' 
WHERE username = 'admin';

-- Step 7: Verify the admin user was created successfully
SELECT username, email, role, first_name, last_name FROM users WHERE username = 'admin';




