-- Safari Database Setup Script
-- Run this script in SQL Server Management Studio or Azure Data Studio

-- Create database
CREATE DATABASE safari_db;
GO

-- Use the database
USE safari_db;
GO

-- Create user for the application
CREATE LOGIN safari_user WITH PASSWORD = '234';
GO

-- Create user in the database
CREATE USER safari_user FOR LOGIN safari_user;
GO

-- Grant permissions
ALTER ROLE db_owner ADD MEMBER safari_user;
GO

-- Grant additional permissions
GRANT CREATE TABLE TO safari_user;
GRANT CREATE PROCEDURE TO safari_user;
GRANT CREATE VIEW TO safari_user;
GO

-- Verify the setup
SELECT name FROM sys.databases WHERE name = 'safari_db';
GO

PRINT 'Database setup completed successfully!';
PRINT 'Database: safari_db';
PRINT 'User: safari_user';
PRINT 'Password: 234';
PRINT 'Connection String: jdbc:sqlserver://localhost:1433;databaseName=safari_db;encrypt=false;trustServerCertificate=true';
GO

