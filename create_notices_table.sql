-- Create notices table for admin messages to drivers and guides
CREATE TABLE notices (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    message TEXT NOT NULL,
    [to] NVARCHAR(20) NOT NULL CHECK ([to] IN ('driver', 'guide')),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    is_read BIT NOT NULL DEFAULT 0
);

-- Add index for better performance
CREATE INDEX IX_notices_to ON notices([to]);
CREATE INDEX IX_notices_created_at ON notices(created_at);
