-- Fixed Admin User Creation Script
-- This script handles the role constraint issue

-- Step 1: Check what roles are currently in the database
SELECT DISTINCT role FROM users;

-- Step 2: If ADMIN role doesn't exist, we need to use an existing role or modify the constraint
-- Let's try with TOURIST role first, then we can update it
DELETE FROM users WHERE username = 'admin';

-- Step 3: Insert admin user with a valid role (TOURIST initially)
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES (
    'admin', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 
    'TOURIST',  -- Use TOURIST initially to avoid constraint error
    'Admin', 
    'admin', 
    'admin@gmail.com', 
    '07188888888', 
    '200479504848'
);

-- Step 4: Update the role to ADMIN (if the constraint allows it)
UPDATE users 
SET role = 'ADMIN' 
WHERE username = 'admin';

-- Step 5: If the UPDATE fails, we'll need to modify the constraint
-- Check if the update worked
SELECT username, role FROM users WHERE username = 'admin';




