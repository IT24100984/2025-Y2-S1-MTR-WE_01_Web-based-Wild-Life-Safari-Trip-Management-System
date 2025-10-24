-- Fix Foreign Key Relationships Only
-- Use this if tables already exist and you just need to fix the relationships

-- Drop existing foreign key constraints if they exist
IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_tours_user_id')
BEGIN
    ALTER TABLE tours DROP CONSTRAINT fk_tours_user_id;
    PRINT 'Dropped existing fk_tours_user_id constraint';
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_drivers_user_id')
BEGIN
    ALTER TABLE drivers DROP CONSTRAINT fk_drivers_user_id;
    PRINT 'Dropped existing fk_drivers_user_id constraint';
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_guides_user_id')
BEGIN
    ALTER TABLE guides DROP CONSTRAINT fk_guides_user_id;
    PRINT 'Dropped existing fk_guides_user_id constraint';
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_tourists_user_id')
BEGIN
    ALTER TABLE tourists DROP CONSTRAINT fk_tourists_user_id;
    PRINT 'Dropped existing fk_tourists_user_id constraint';
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_tourist_users_user_id')
BEGIN
    ALTER TABLE tourist_users DROP CONSTRAINT fk_tourist_users_user_id;
    PRINT 'Dropped existing fk_tourist_users_user_id constraint';
END

-- Add foreign key constraints
ALTER TABLE tours 
ADD CONSTRAINT fk_tours_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);
PRINT 'Added fk_tours_user_id constraint';

ALTER TABLE drivers 
ADD CONSTRAINT fk_drivers_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);
PRINT 'Added fk_drivers_user_id constraint';

ALTER TABLE guides 
ADD CONSTRAINT fk_guides_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);
PRINT 'Added fk_guides_user_id constraint';

ALTER TABLE tourists 
ADD CONSTRAINT fk_tourists_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);
PRINT 'Added fk_tourists_user_id constraint';

ALTER TABLE tourist_users 
ADD CONSTRAINT fk_tourist_users_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);
PRINT 'Added fk_tourist_users_user_id constraint';

-- Verify constraints were created
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
WHERE fk.name LIKE 'fk_%_user_id'
ORDER BY fk.name;

PRINT 'Foreign key relationships fixed successfully!';




