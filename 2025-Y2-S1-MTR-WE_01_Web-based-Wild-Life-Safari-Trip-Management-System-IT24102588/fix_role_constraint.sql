-- Script to fix the role constraint to allow ADMIN role

-- Step 1: Find the constraint name
SELECT 
    CONSTRAINT_NAME,
    CHECK_CLAUSE
FROM INFORMATION_SCHEMA.CHECK_CONSTRAINTS 
WHERE CONSTRAINT_NAME LIKE '%role%';

-- Step 2: Drop the existing constraint (replace 'CK__users__role__49C3F6B7' with actual constraint name)
-- ALTER TABLE users DROP CONSTRAINT CK__users__role__49C3F6B7;

-- Step 3: Add a new constraint that allows ADMIN role
-- ALTER TABLE users ADD CONSTRAINT CK_users_role 
-- CHECK (role IN ('TOURIST', 'GUIDE', 'DRIVER', 'ADMIN'));

-- Alternative: If you can't modify the constraint, use this approach:
-- 1. Insert with TOURIST role
-- 2. Update to ADMIN role
-- 3. If that fails, we'll need to work with the existing roles




