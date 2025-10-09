# Driver and Guide Database Schema

## Table: `drivers`

The `drivers` table stores driver-specific information for users with DRIVER role.

### Columns:

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique driver ID |
| `user_id` | BIGINT | NOT NULL, FOREIGN KEY | Reference to users table |
| `license_number` | VARCHAR(255) | NOT NULL, UNIQUE | Driver's license number |
| `vehicle_type` | VARCHAR(100) | NOT NULL | Type of vehicle (Jeep, Van, etc.) |
| `experience_years` | INT | NOT NULL | Years of driving experience |
| `languages` | VARCHAR(255) | NOT NULL | Languages spoken by driver |
| `description` | TEXT | NULL | Driver's professional description |
| `is_available` | BOOLEAN | NOT NULL, DEFAULT TRUE | Driver availability status |
| `rating` | DECIMAL(3,2) | DEFAULT 0.0 | Driver's average rating |
| `total_trips` | INT | DEFAULT 0 | Total number of trips completed |
| `created_date` | DATE | NOT NULL | Date when driver profile was created |

### Sample Data:

```sql
INSERT INTO drivers (user_id, license_number, vehicle_type, experience_years, languages, description, is_available, rating, total_trips, created_date) 
VALUES 
(2, 'DL123456789', 'Jeep', 5, 'English, Sinhala', 'Experienced safari driver with 5 years of expertise.', true, 4.5, 25, '2024-01-15'),
(4, 'DL987654321', 'Van', 8, 'English, Tamil', 'Professional driver specializing in group tours.', true, 4.8, 45, '2024-01-20');
```

## Table: `guides`

The `guides` table stores guide-specific information for users with GUIDE role.

### Columns:

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique guide ID |
| `user_id` | BIGINT | NOT NULL, FOREIGN KEY | Reference to users table |
| `experience_years` | INT | NOT NULL | Years of guiding experience |
| `languages` | VARCHAR(255) | NOT NULL | Languages spoken by guide |
| `specializations` | TEXT | NULL | Guide's areas of expertise |
| `description` | TEXT | NULL | Guide's professional description |
| `is_available` | BOOLEAN | NOT NULL, DEFAULT TRUE | Guide availability status |
| `rating` | DECIMAL(3,2) | DEFAULT 0.0 | Guide's average rating |
| `total_tours` | INT | DEFAULT 0 | Total number of tours completed |
| `certifications` | TEXT | NULL | Guide's professional certifications |
| `created_date` | DATE | NOT NULL | Date when guide profile was created |

### Sample Data:

```sql
INSERT INTO guides (user_id, experience_years, languages, specializations, description, is_available, rating, total_tours, certifications, created_date) 
VALUES 
(3, 6, 'English, Sinhala, Tamil', 'Wildlife tours, Cultural experiences', 'Professional wildlife guide with 6 years of experience.', true, 4.7, 30, 'Wildlife Guide Certification', '2024-01-18'),
(5, 4, 'English, French', 'Bird watching, Photography tours', 'Specialized in bird watching and photography tours.', true, 4.6, 20, 'Bird Watching Guide Certification', '2024-01-22');
```

## Relationships:

### Foreign Key Relationships:
- `drivers.user_id` → `users.id`
- `guides.user_id` → `users.id`

### Business Logic:
1. **User Registration**: When a user registers with DRIVER or GUIDE role, corresponding records are created in `drivers` or `guides` table
2. **Profile Management**: Drivers and guides can update their descriptions, availability, and other professional details
3. **Rating System**: Both tables support rating and total trip/tour tracking
4. **Availability**: Both tables track availability status for tour assignment

## API Endpoints:

### Driver Endpoints:
- **GET** `/api/drivers` - Get all drivers
- **GET** `/api/drivers/available` - Get available drivers
- **GET** `/api/drivers/user/{userId}` - Get driver by user ID
- **PUT** `/api/drivers/{userId}/description` - Update driver description
- **PUT** `/api/drivers/{userId}/availability` - Update driver availability

### Guide Endpoints:
- **GET** `/api/guides` - Get all guides
- **GET** `/api/guides/available` - Get available guides
- **GET** `/api/guides/user/{userId}` - Get guide by user ID
- **PUT** `/api/guides/{userId}/description` - Update guide description
- **PUT** `/api/guides/{userId}/availability` - Update guide availability

## Registration Flow:

1. **User Registration**: User fills signup form with role selection
2. **User Creation**: User record created in `users` table
3. **Role-Specific Creation**: 
   - If DRIVER → Record created in `drivers` table
   - If GUIDE → Record created in `guides` table
   - If TOURIST → No additional table record needed
4. **Dashboard Redirect**: User redirected to appropriate dashboard based on role

