-- Check what roles currently exist in the database
SELECT DISTINCT role FROM users;

-- Check if there are any existing users
SELECT COUNT(*) as user_count FROM users;

-- Check the structure of the users table
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'users'
ORDER BY ORDINAL_POSITION;

