-- Complete Database Reset Script
-- This script will drop and recreate all tables with proper foreign key relationships

-- Drop all foreign key constraints first
IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_tours_user_id')
BEGIN
    ALTER TABLE tours DROP CONSTRAINT fk_tours_user_id;
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_drivers_user_id')
BEGIN
    ALTER TABLE drivers DROP CONSTRAINT fk_drivers_user_id;
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_guides_user_id')
BEGIN
    ALTER TABLE guides DROP CONSTRAINT fk_guides_user_id;
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_tourists_user_id')
BEGIN
    ALTER TABLE tourists DROP CONSTRAINT fk_tourists_user_id;
END

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_tourist_users_user_id')
BEGIN
    ALTER TABLE tourist_users DROP CONSTRAINT fk_tourist_users_user_id;
END

-- Drop all tables in correct order (child tables first)
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'driver_tours')
BEGIN
    DROP TABLE driver_tours;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'guide_tours')
BEGIN
    DROP TABLE guide_tours;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'tourist_bookings')
BEGIN
    DROP TABLE tourist_bookings;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'guide_bookings')
BEGIN
    DROP TABLE guide_bookings;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'driver_bookings')
BEGIN
    DROP TABLE driver_bookings;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'tours')
BEGIN
    DROP TABLE tours;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'drivers')
BEGIN
    DROP TABLE drivers;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'guides')
BEGIN
    DROP TABLE guides;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'tourists')
BEGIN
    DROP TABLE tourists;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'tourist_users')
BEGIN
    DROP TABLE tourist_users;
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'users')
BEGIN
    DROP TABLE users;
END

-- Create users table with correct structure
CREATE TABLE users (
    user_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    contact_number VARCHAR(20) NOT NULL,
    nic VARCHAR(20) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    nationality VARCHAR(100),
    experience INT,
    languages VARCHAR(500),
    license_number VARCHAR(50),
    vehicle_type VARCHAR(50)
);

-- Create tours table
CREATE TABLE tours (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    tour_name VARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instructions TEXT,
    user_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    assigned_driver_id BIGINT,
    assigned_guide_id BIGINT,
    created_date DATETIME DEFAULT GETDATE()
);

-- Create drivers table
CREATE TABLE drivers (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    license_number VARCHAR(50) NOT NULL UNIQUE,
    vehicle_type VARCHAR(50) NOT NULL,
    experience_years INT NOT NULL,
    languages VARCHAR(500) NOT NULL,
    description TEXT,
    is_available BIT NOT NULL DEFAULT 1,
    rating DECIMAL(3,2) DEFAULT 0.0,
    total_trips INT DEFAULT 0,
    created_date DATE DEFAULT GETDATE()
);

-- Create guides table
CREATE TABLE guides (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    experience_years INT NOT NULL,
    languages VARCHAR(500) NOT NULL,
    specializations TEXT,
    description TEXT,
    is_available BIT NOT NULL DEFAULT 1,
    rating DECIMAL(3,2) DEFAULT 0.0,
    total_tours INT DEFAULT 0,
    certifications TEXT,
    created_date DATE DEFAULT GETDATE()
);

-- Create tourists table
CREATE TABLE tourists (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    nationality VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    nic VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL
);

-- Create tourist_users table
CREATE TABLE tourist_users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    nationality VARCHAR(100) NOT NULL,
    nic VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL
);

-- Create driver_tours table
CREATE TABLE driver_tours (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    driver_id BIGINT NOT NULL,
    tour_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_date DATETIME DEFAULT GETDATE()
);

-- Create guide_tours table
CREATE TABLE guide_tours (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    guide_id BIGINT NOT NULL,
    tour_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_date DATETIME DEFAULT GETDATE()
);

-- Create tourist_bookings table
CREATE TABLE tourist_bookings (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL UNIQUE,
    tourist_id BIGINT NOT NULL,
    tour_name VARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instruction TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_date DATETIME DEFAULT GETDATE()
);

-- Create guide_bookings table
CREATE TABLE guide_bookings (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL UNIQUE,
    guide_id BIGINT NOT NULL,
    tour_name VARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instruction TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_date DATETIME DEFAULT GETDATE()
);

-- Create driver_bookings table
CREATE TABLE driver_bookings (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL UNIQUE,
    driver_id BIGINT NOT NULL,
    tour_name VARCHAR(255) NOT NULL,
    tour_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instruction TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_date DATETIME DEFAULT GETDATE()
);

-- Add foreign key constraints
ALTER TABLE tours 
ADD CONSTRAINT fk_tours_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE drivers 
ADD CONSTRAINT fk_drivers_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE guides 
ADD CONSTRAINT fk_guides_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE tourists 
ADD CONSTRAINT fk_tourists_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE tourist_users 
ADD CONSTRAINT fk_tourist_users_user_id 
FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE driver_tours 
ADD CONSTRAINT fk_driver_tours_driver_id 
FOREIGN KEY (driver_id) REFERENCES drivers(id);

ALTER TABLE driver_tours 
ADD CONSTRAINT fk_driver_tours_tour_id 
FOREIGN KEY (tour_id) REFERENCES tours(id);

ALTER TABLE guide_tours 
ADD CONSTRAINT fk_guide_tours_guide_id 
FOREIGN KEY (guide_id) REFERENCES guides(id);

ALTER TABLE guide_tours 
ADD CONSTRAINT fk_guide_tours_tour_id 
FOREIGN KEY (tour_id) REFERENCES tours(id);

-- Insert sample data
INSERT INTO users (username, password, first_name, last_name, email, contact_number, nic, role, nationality) 
VALUES ('tourist1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'John', 'Doe', 'john@example.com', '1234567890', '123456789V', 'TOURIST', 'American');

INSERT INTO users (username, password, first_name, last_name, email, contact_number, nic, role, experience, languages, license_number, vehicle_type) 
VALUES ('driver1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Mike', 'Smith', 'mike@example.com', '0987654321', '987654321V', 'DRIVER', 5, 'English,Sinhala', 'DL123456', 'JEEP');

INSERT INTO users (username, password, first_name, last_name, email, contact_number, nic, role, experience, languages) 
VALUES ('guide1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Sarah', 'Johnson', 'sarah@example.com', '1122334455', '112233445V', 'GUIDE', 3, 'English,Sinhala,Tamil');

-- Create corresponding records in role-specific tables
INSERT INTO tourists (user_id, email, nationality, first_name, last_name, contact_number, nic, username)
SELECT user_id, email, nationality, first_name, last_name, contact_number, nic, username 
FROM users WHERE role = 'TOURIST';

INSERT INTO tourist_users (user_id, email, nationality, nic, username)
SELECT user_id, email, nationality, nic, username 
FROM users WHERE role = 'TOURIST';

INSERT INTO drivers (user_id, license_number, vehicle_type, experience_years, languages, description)
SELECT user_id, license_number, vehicle_type, experience, languages, 'Experienced safari driver'
FROM users WHERE role = 'DRIVER';

INSERT INTO guides (user_id, experience_years, languages, specializations, description)
SELECT user_id, experience, languages, 'Wildlife, Photography', 'Professional wildlife guide'
FROM users WHERE role = 'GUIDE';

-- Verify the setup
SELECT 'Database reset completed successfully!' AS status;
SELECT COUNT(*) AS user_count FROM users;
SELECT COUNT(*) AS driver_count FROM drivers;
SELECT COUNT(*) AS guide_count FROM guides;
SELECT COUNT(*) AS tourist_count FROM tourists;




