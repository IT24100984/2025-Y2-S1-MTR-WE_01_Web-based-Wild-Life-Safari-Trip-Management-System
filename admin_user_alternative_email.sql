-- Alternative Solution: Use Different Email
-- This avoids the duplicate email constraint issue

-- Step 1: Delete any existing admin user
DELETE FROM users WHERE username = 'admin';

-- Step 2: Insert admin user with different email
INSERT INTO users (username, password, role, first_name, last_name, email, contact_number, nic) 
VALUES (
    'admin', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 
    'TOURIST',  -- Use TOURIST role to avoid constraint issues
    'Admin', 
    'admin', 
    'admin@safari.com',  -- Different email to avoid constraint
    '07188888888', 
    '200479504848'
);

-- Step 3: Try to update to ADMIN role (if constraint allows)
UPDATE users 
SET role = 'ADMIN' 
WHERE username = 'admin';

-- Step 4: Verify the admin user was created successfully
SELECT username, email, role, first_name, last_name FROM users WHERE username = 'admin';




