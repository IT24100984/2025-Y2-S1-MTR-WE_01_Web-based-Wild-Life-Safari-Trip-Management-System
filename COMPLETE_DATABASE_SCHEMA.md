# Complete Database Schema - Tourist, Driver, and Guide Tables

## Table: `tourists`

The `tourists` table stores tourist-specific information for users with TOURIST role.

### Columns:

| Column Name | Data Type | Constraints | Description |
|-------------|-----------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique tourist ID |
| `user_id` | BIGINT | NOT NULL, FOREIGN KEY | Reference to users table |
| `nationality` | VARCHAR(100) | NOT NULL | Tourist's nationality |
| `preferred_language` | VARCHAR(50) | NOT NULL | Tourist's preferred language |
| `travel_preferences` | TEXT | NULL | Tourist's travel preferences |
| `dietary_requirements` | TEXT | NULL | Tourist's dietary restrictions |
| `emergency_contact` | VARCHAR(20) | NOT NULL | Emergency contact number |
| `passport_number` | VARCHAR(50) | NULL | Tourist's passport number |
| `visa_status` | VARCHAR(50) | NULL | Tourist's visa status |
| `total_bookings` | INT | DEFAULT 0 | Total number of bookings made |
| `loyalty_points` | INT | DEFAULT 0 | Tourist's loyalty points |
| `created_date` | DATE | NOT NULL | Date when tourist profile was created |

### Sample Data:

```sql
INSERT INTO tourists (user_id, nationality, preferred_language, travel_preferences, dietary_requirements, emergency_contact, passport_number, visa_status, total_bookings, loyalty_points, created_date) 
VALUES 
(1, 'American', 'English', 'Wildlife photography, Cultural tours', 'Vegetarian', '+1234567890', 'A12345678', 'Valid', 3, 150, '2024-01-15'),
(6, 'British', 'English', 'Adventure tours, Bird watching', 'No restrictions', '+44123456789', 'B87654321', 'Valid', 1, 50, '2024-01-20');
```

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
(7, 'DL987654321', 'Van', 8, 'English, Tamil', 'Professional driver specializing in group tours.', true, 4.8, 45, '2024-01-20');
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
(8, 4, 'English, French', 'Bird watching, Photography tours', 'Specialized in bird watching and photography tours.', true, 4.6, 20, 'Bird Watching Guide Certification', '2024-01-22');
```

## Relationships:

### Foreign Key Relationships:
- `tourists.user_id` → `users.id`
- `drivers.user_id` → `users.id`
- `guides.user_id` → `users.id`

### Business Logic:

#### **User Registration Flow:**
1. **User Registration**: User fills signup form with role selection
2. **User Creation**: User record created in `users` table
3. **Role-Specific Creation**: 
   - If TOURIST → Record created in `tourists` table
   - If DRIVER → Record created in `drivers` table
   - If GUIDE → Record created in `guides` table
4. **Dashboard Redirect**: User redirected to appropriate dashboard based on role

#### **Tourist Features:**
- **Profile Management**: Update travel preferences, dietary requirements
- **Document Management**: Passport number, visa status
- **Loyalty System**: Points accumulation, booking tracking
- **Emergency Contact**: Contact information for safety

#### **Driver Features:**
- **License Management**: License number tracking
- **Vehicle Information**: Vehicle type and specifications
- **Availability**: Available/unavailable status
- **Performance**: Rating and trip count tracking

#### **Guide Features:**
- **Specializations**: Areas of expertise
- **Certifications**: Professional qualifications
- **Availability**: Available/unavailable status
- **Performance**: Rating and tour count tracking

## API Endpoints:

### Tourist Endpoints:
- **GET** `/api/tourists` - Get all tourists
- **GET** `/api/tourists/user/{userId}` - Get tourist by user ID
- **PUT** `/api/tourists/{userId}/preferences` - Update travel preferences
- **PUT** `/api/tourists/{userId}/documents` - Update passport/visa info
- **POST** `/api/tourists/{userId}/loyalty` - Add loyalty points

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

## Registration Flow Summary:

### Tourist Registration:
1. **Form Data**: Nationality, preferred language, contact info
2. **Database**: Creates `users` + `tourists` records
3. **Redirect**: Goes to login page (tourists use general dashboard)

### Driver Registration:
1. **Form Data**: License number, experience, languages
2. **Database**: Creates `users` + `drivers` records
3. **Redirect**: Goes to driver dashboard

### Guide Registration:
1. **Form Data**: Experience, languages, specializations
2. **Database**: Creates `users` + `guides` records
3. **Redirect**: Goes to guide dashboard




