# Individual User Booking Tables System

## Overview

This system creates individual booking tables for each user type (Tourist, Guide, Driver) that store their specific bookings with the requested fields.

## Database Tables Created

### 1. Tourist Bookings Table (`tourist_bookings`)

```sql
CREATE TABLE tourist_bookings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id VARCHAR(255) NOT NULL UNIQUE,
    tourist_id BIGINT NOT NULL,
    tour_name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instruction TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    action TEXT,
    created_date DATE NOT NULL
);
```

### 2. Guide Bookings Table (`guide_bookings`)

```sql
CREATE TABLE guide_bookings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id VARCHAR(255) NOT NULL UNIQUE,
    guide_id BIGINT NOT NULL,
    tour_name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instruction TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    action TEXT,
    created_date DATE NOT NULL
);
```

### 3. Driver Bookings Table (`driver_bookings`)

```sql
CREATE TABLE driver_bookings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id VARCHAR(255) NOT NULL UNIQUE,
    driver_id BIGINT NOT NULL,
    tour_name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    number_of_people INT NOT NULL,
    special_instruction TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    action TEXT,
    created_date DATE NOT NULL
);
```

## Fields in Each Table

| Field | Type | Description |
|-------|------|-------------|
| `id` | BIGINT | Primary key, auto-increment |
| `booking_id` | VARCHAR(255) | Unique booking identifier (auto-generated) |
| `tourist_id/guide_id/driver_id` | BIGINT | Reference to the user |
| `tour_name` | VARCHAR(255) | Name of the tour |
| `date` | DATE | Booking date |
| `number_of_people` | INT | Number of people for the booking |
| `special_instruction` | TEXT | Special instructions for the booking |
| `status` | VARCHAR(50) | Booking status (PENDING, CONFIRMED, CANCELLED, COMPLETED) |
| `action` | TEXT | Action taken on the booking |
| `created_date` | DATE | When the booking was created |

## API Endpoints

### Tourist Bookings API (`/api/tourist-bookings`)

- **POST** `/api/tourist-bookings` - Create new tourist booking
- **GET** `/api/tourist-bookings` - Get all tourist bookings
- **GET** `/api/tourist-bookings/tourist/{touristId}` - Get bookings by tourist ID
- **GET** `/api/tourist-bookings/booking/{bookingId}` - Get booking by booking ID
- **GET** `/api/tourist-bookings/status/{status}` - Get bookings by status
- **GET** `/api/tourist-bookings/tourist/{touristId}/status/{status}` - Get bookings by tourist and status
- **GET** `/api/tourist-bookings/date/{date}` - Get bookings by date
- **GET** `/api/tourist-bookings/tourist/{touristId}/date/{date}` - Get bookings by tourist and date
- **GET** `/api/tourist-bookings/tour/{tourName}` - Get bookings by tour name
- **GET** `/api/tourist-bookings/tourist/{touristId}/tour/{tourName}` - Get bookings by tourist and tour
- **PUT** `/api/tourist-bookings/{bookingId}/status` - Update booking status
- **PUT** `/api/tourist-bookings/{bookingId}/action` - Update booking action
- **DELETE** `/api/tourist-bookings/{bookingId}` - Delete booking

### Guide Bookings API (`/api/guide-bookings`)

- **POST** `/api/guide-bookings` - Create new guide booking
- **GET** `/api/guide-bookings` - Get all guide bookings
- **GET** `/api/guide-bookings/guide/{guideId}` - Get bookings by guide ID
- **GET** `/api/guide-bookings/booking/{bookingId}` - Get booking by booking ID
- **GET** `/api/guide-bookings/status/{status}` - Get bookings by status
- **GET** `/api/guide-bookings/guide/{guideId}/status/{status}` - Get bookings by guide and status
- **GET** `/api/guide-bookings/date/{date}` - Get bookings by date
- **GET** `/api/guide-bookings/guide/{guideId}/date/{date}` - Get bookings by guide and date
- **GET** `/api/guide-bookings/tour/{tourName}` - Get bookings by tour name
- **GET** `/api/guide-bookings/guide/{guideId}/tour/{tourName}` - Get bookings by guide and tour
- **PUT** `/api/guide-bookings/{bookingId}/status` - Update booking status
- **PUT** `/api/guide-bookings/{bookingId}/action` - Update booking action
- **DELETE** `/api/guide-bookings/{bookingId}` - Delete booking

### Driver Bookings API (`/api/driver-bookings`)

- **POST** `/api/driver-bookings` - Create new driver booking
- **GET** `/api/driver-bookings` - Get all driver bookings
- **GET** `/api/driver-bookings/driver/{driverId}` - Get bookings by driver ID
- **GET** `/api/driver-bookings/booking/{bookingId}` - Get booking by booking ID
- **GET** `/api/driver-bookings/status/{status}` - Get bookings by status
- **GET** `/api/driver-bookings/driver/{driverId}/status/{status}` - Get bookings by driver and status
- **GET** `/api/driver-bookings/date/{date}` - Get bookings by date
- **GET** `/api/driver-bookings/driver/{driverId}/date/{date}` - Get bookings by driver and date
- **GET** `/api/driver-bookings/tour/{tourName}` - Get bookings by tour name
- **GET** `/api/driver-bookings/driver/{driverId}/tour/{tourName}` - Get bookings by driver and tour
- **PUT** `/api/driver-bookings/{bookingId}/status` - Update booking status
- **PUT** `/api/driver-bookings/{bookingId}/action` - Update booking action
- **DELETE** `/api/driver-bookings/{bookingId}` - Delete booking

## Sample Data

### Tourist Booking Example:
```json
{
  "touristId": 1,
  "tourName": "Yala National Park",
  "date": "2024-02-15",
  "numberOfPeople": 4,
  "specialInstruction": "Vegetarian meals required"
}
```

### Guide Booking Example:
```json
{
  "guideId": 2,
  "tourName": "Sinharaja Forest",
  "date": "2024-02-20",
  "numberOfPeople": 6,
  "specialInstruction": "Bird watching tour"
}
```

### Driver Booking Example:
```json
{
  "driverId": 3,
  "tourName": "Wilpattu National Park",
  "date": "2024-02-25",
  "numberOfPeople": 8,
  "specialInstruction": "Early morning pickup at 5 AM"
}
```

## Booking Status Values

- **PENDING**: Booking is waiting for confirmation
- **CONFIRMED**: Booking has been confirmed
- **CANCELLED**: Booking has been cancelled
- **COMPLETED**: Booking has been completed

## Booking ID Format

- **Tourist Bookings**: `TB{timestamp}_{touristId}` (e.g., `TB1704067200000_1`)
- **Guide Bookings**: `GB{timestamp}_{guideId}` (e.g., `GB1704067200000_2`)
- **Driver Bookings**: `DB{timestamp}_{driverId}` (e.g., `DB1704067200000_3`)

## How It Works

1. **User Registration**: When a user registers, they get a record in their respective table (tourists, guides, drivers)
2. **Booking Creation**: When a booking is made, it's stored in the appropriate booking table
3. **Individual Tables**: Each user type has their own booking table with the same structure
4. **Status Management**: Bookings can be updated with status and action information
5. **Query Flexibility**: Multiple ways to query bookings by user, date, status, tour name, etc.

## Security

All booking endpoints are publicly accessible (no authentication required) as configured in SecurityConfig.

## What This Achieves

- ✅ **Individual User Tables**: Each user type has their own booking table
- ✅ **Required Fields**: All requested fields are included (booking_id, tour_name, date, number_of_people, special_instruction, status, action)
- ✅ **Automatic Creation**: Tables are created automatically when the application starts
- ✅ **Full CRUD Operations**: Create, Read, Update, Delete operations for all booking types
- ✅ **Flexible Queries**: Multiple ways to query and filter bookings
- ✅ **Status Management**: Track booking status and actions
- ✅ **REST API**: Complete REST API for all booking operations




