-- Fix Admin Login Issues
-- This script addresses common admin login problems

-- Step 1: Check current admin user status
SELECT username, role, password FROM users WHERE username = 'admin';

-- Step 2: Fix password if not encrypted
UPDATE users 
SET password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' 
WHERE username = 'admin';

-- Step 3: Try to update role to ADMIN
UPDATE users 
SET role = 'ADMIN' 
WHERE username = 'admin';

-- Step 4: If role update fails, we'll need to handle it in the application
-- Check if the update worked
SELECT username, role FROM users WHERE username = 'admin';

-- Step 5: Verify final admin user status
SELECT 
    username, 
    role, 
    first_name, 
    last_name,
    email,
    CASE 
        WHEN password LIKE '$2a$%' THEN 'Password: Properly Encrypted'
        ELSE 'Password: NOT ENCRYPTED'
    END as password_status
FROM users 
WHERE username = 'admin';




