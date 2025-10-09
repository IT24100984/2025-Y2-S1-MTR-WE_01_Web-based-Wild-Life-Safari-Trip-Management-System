-- Add assigned driver and guide columns to tours table
ALTER TABLE tours 
ADD assigned_driver_id BIGINT NULL,
    assigned_guide_id BIGINT NULL;

-- Add foreign key constraints (optional, for data integrity)
-- ALTER TABLE tours ADD CONSTRAINT FK_tours_driver FOREIGN KEY (assigned_driver_id) REFERENCES drivers(id);
-- ALTER TABLE tours ADD CONSTRAINT FK_tours_guide FOREIGN KEY (assigned_guide_id) REFERENCES guides(id);
