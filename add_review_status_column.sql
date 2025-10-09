-- Add status column to reviews table for approval system
-- This script adds a status column to track review approval status

-- Add status column with default value 'PENDING'
ALTER TABLE reviews 
ADD status NVARCHAR(20) NOT NULL DEFAULT 'PENDING';

-- Add check constraint to ensure valid status values
ALTER TABLE reviews 
ADD CONSTRAINT CK_reviews_status 
CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED'));

-- Create index on status for better performance when filtering by status
CREATE INDEX IX_reviews_status ON reviews(status);

-- Update existing reviews to have APPROVED status (so they continue to show on reviews page)
UPDATE reviews 
SET status = 'APPROVED' 
WHERE status = 'PENDING';

-- Verify the changes
SELECT id, name, review, stars, created_at, status 
FROM reviews 
ORDER BY created_at DESC;
