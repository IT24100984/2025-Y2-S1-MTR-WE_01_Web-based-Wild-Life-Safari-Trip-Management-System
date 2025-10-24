-- Fix foreign key relationship between tours and users tables
-- This script adds the proper foreign key constraint

-- First, check if the foreign key already exists and drop it if it does
IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_tours_user_id')
BEGIN
    ALTER TABLE tours DROP CONSTRAINT fk_tours_user_id;
END

-- Add the proper foreign key constraint
ALTER TABLE tours 
ADD CONSTRAINT fk_tours_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);

-- Verify the constraint was created
SELECT 
    fk.name AS constraint_name,
    tp.name AS parent_table,
    cp.name AS parent_column,
    tr.name AS referenced_table,
    cr.name AS referenced_column
FROM sys.foreign_keys fk
INNER JOIN sys.tables tp ON fk.parent_object_id = tp.object_id
INNER JOIN sys.tables tr ON fk.referenced_object_id = tr.object_id
INNER JOIN sys.foreign_key_columns fkc ON fk.object_id = fkc.constraint_object_id
INNER JOIN sys.columns cp ON fkc.parent_column_id = cp.column_id AND fkc.parent_object_id = cp.object_id
INNER JOIN sys.columns cr ON fkc.referenced_column_id = cr.column_id AND fkc.referenced_object_id = cr.object_id
WHERE fk.name = 'fk_tours_user_id';




