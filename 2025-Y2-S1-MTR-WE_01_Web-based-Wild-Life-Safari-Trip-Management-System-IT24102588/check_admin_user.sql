-- Check Admin User Details
-- Run this to verify the admin user is set up correctly

-- Step 1: Check if admin user exists
SELECT 
    username, 
    email, 
    role, 
    first_name, 
    last_name,
    contact_number,
    nic
FROM users 
WHERE username = 'admin';

-- Step 2: Check password encryption (should start with $2a$)
SELECT 
    username, 
    password,
    CASE 
        WHEN password LIKE '$2a$%' THEN 'Properly Encrypted'
        ELSE 'NOT ENCRYPTED - NEEDS FIX'
    END as password_status
FROM users 
WHERE username = 'admin';

-- Step 3: Check if there are any other users with admin@gmail.com
SELECT 
    username, 
    email, 
    role 
FROM users 
WHERE email = 'admin@gmail.com';

-- Step 4: Check total user count
SELECT COUNT(*) as total_users FROM users;

-- Step 5: Check all roles in the database
SELECT DISTINCT role FROM users;




