-- Create contact_details table
CREATE TABLE contact_details (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    address TEXT NOT NULL,
    phone NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) NOT NULL
);

-- Insert default contact details
INSERT INTO contact_details (address, phone, email) 
VALUES ('123 Jungle Lane, Wildlands', '+94 123 456 789', 'info@wildlifesafari.com');
