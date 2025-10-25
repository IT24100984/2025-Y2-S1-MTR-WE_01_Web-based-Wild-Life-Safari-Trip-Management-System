-- Create Reviews Table
-- This script creates the reviews table for storing customer reviews

CREATE TABLE reviews (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         name NVARCHAR(255) NOT NULL,
                         review TEXT NOT NULL,
                         stars INT NOT NULL CHECK (stars >= 1 AND stars <= 5),
                         created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                         status NVARCHAR(20) NOT NULL DEFAULT 'PENDING'
);

-- Create index on created_at for better performance when ordering by date
CREATE INDEX IX_reviews_created_at ON reviews(created_at DESC);

-- Create index on stars for filtering by rating
CREATE INDEX IX_reviews_stars ON reviews(stars);

-- Create index on status for filtering by approval status
CREATE INDEX IX_reviews_status ON reviews(status);

-- Insert some sample reviews for testing
INSERT INTO reviews (name, review, stars, status) VALUES
                                                      ('John Smith', 'Amazing safari experience! The guide was knowledgeable and we saw so many animals. Highly recommended!', 5, 'APPROVED'),
                                                      ('Sarah Johnson', 'Great tour with excellent service. The driver was professional and the vehicle was comfortable.', 4, 'APPROVED'),
                                                      ('Mike Wilson', 'Good experience overall, but the weather was not great. Still managed to see some wildlife.', 3, 'APPROVED'),
                                                      ('Emma Davis', 'Outstanding! Best safari tour I have ever been on. The guide knew exactly where to find the animals.', 5, 'APPROVED'),
                                                      ('David Brown', 'Average experience. The tour was okay but nothing special.', 2, 'APPROVED');

-- Verify the table was created correctly
SELECT * FROM reviews ORDER BY created_at DESC;